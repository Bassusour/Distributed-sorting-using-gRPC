package distrSortTest

import java.time._
import java.util.concurrent.TimeUnit
import java.util.logging.{Level, Logger}
import protoDistrSorting.distrSort.{DistrSortingGrpc, ID, KeyRange, DummyText, PartitionedValues, Partition, 
                                    Dataset, Data, ConnectionInformation, ConnectionInformations}
import protoDistrSorting.workerCommunication.{WorkerConnectionsGrpc}
import protoDistrSorting.workerCommunication.WorkerConnectionsGrpc.WorkerConnectionsStub
import protoDistrSorting.distrSort.DistrSortingGrpc.DistrSortingBlockingStub
import io.grpc.{StatusRuntimeException, ManagedChannelBuilder, ManagedChannel}
import scala.io.Source
import java.io._
import scala.concurrent.{Await, ExecutionContext, Future, Promise}
import scala.concurrent.duration.Duration
import ExecutionContext.Implicits.global
import io.grpc.Grpc
import io.grpc.{Server, ServerBuilder}
import io.grpc.ServerInterceptors;
import io.grpc.stub.StreamObserver;
import scala.language.postfixOps

// Companion object
object Worker {
  // Constructor
  def apply(host: String, port: Int, outputDirectory: String): Worker = {
    val channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build
    val blockingStub = DistrSortingGrpc.blockingStub(channel)
    new Worker(channel, blockingStub, outputDirectory)
  }

  def main(args: Array[String]): Unit = {
    val masterIP = args(0).split(":") 
    val host = masterIP(0)
    val port = masterIP(1).toInt
    var inputDirectories: List[String] = List()
    var outputDirectory = ""
    var numberKeys = 0
    var withGeneratedFiles = 0
    if(args(1) == "-I"){
      withGeneratedFiles = 1
      var indice = 2
      while (args(indice) != "-O"){
        inputDirectories = args(indice) :: inputDirectories
        indice = indice + 1
      }
      outputDirectory = args(indice +1)
    }
    else if(args(1) == "-N") numberKeys = args(2).toInt

    val client = Worker(host, port, outputDirectory)
    try {
      client.getID()
      client.sendConnectionInformation()
      
      val sorting = new sorting()
      var locallySorted : List[String] = List()
      if(withGeneratedFiles == 0){
        sorting.generateData("data" + client.getID(), numberKeys)
        locallySorted = sorting.toList("data")
      }
      else locallySorted = sorting.getData(inputDirectories, 0)

      // Local sort
      locallySorted = locallySorted.sorted

      // Get min and max key
      val min = locallySorted.head
      val max = locallySorted.last
      client.sendKeyRange(min, max)
      
      client.makeServer()
      
      while(!client.askIfDonePartitioning()) {
        Thread.sleep(1000)
      }
      
      client.getPartitions()

      // Split keys into partitions here (and sort them) - Edwige
      println(client.allPartitions)
      val partitionedList = sorting.separatePartition(client.allPartitions, locallySorted, client.globalMaxKey)
      var indice = 0
      for(part <- partitionedList){
        println(part)
        sorting.writeInFile(part, outputDirectory + "/partition" + client.id + "." + indice)
        indice = indice +1
      }
      
      val connectionInformation = client.getConnectionInformations
      val stubs = client.makeStubs(connectionInformation)

      // Sends partitions to correct workers
      for {
        (stub, id) <- stubs
        partition = client.getPartition(id)
      } yield(stub.getWantedPartitions(partition))

      Thread.sleep(1000)

      // sort local partitions - Edwige
      //val localPartition = sorting.getLocalKeys(outputDirectory, client.id, client.noWorkers)
      val localPartition = sorting.getData(List(outputDirectory), 1)
      sorting.writeInFile(localPartition.sorted, outputDirectory + "/partition." + client.id)

      client.workerServer.awaitTermination(2, TimeUnit.SECONDS)
    } finally {
      client.shutdown()
    }
  }
}

class Worker private(
  private val channel: ManagedChannel,
  private val blockingStub: DistrSortingBlockingStub,
  private val outputDirectory: String
) { self =>
  var id: Int = 0;
  private var globalMaxKey = ""
  private var allPartitions: Seq[Partition] = Seq()
  private var noWorkers: Int = 0
  private var host: String = ""
  private var port: Int = 0
  private var workerServer: Server = null
  private[this] val logger = Logger.getLogger(classOf[Worker].getName)

  def shutdown(): Unit = {
    channel.shutdown.awaitTermination(5, TimeUnit.SECONDS)
  }

  def getID(): Unit = {
    val request = DummyText(dummyText = "Give me an ID")
    val response = blockingStub.assignID(request)
    // logger.info("ID: " + response.id)
    id = response.id;
    host = "127.0.0.1"
    port = 50052+id
  }

  def sendConnectionInformation(): Unit = {
    val request = ConnectionInformation(host = host, port = port, id = id)
    val response = blockingStub.getConnectionInformation(request)
    // logger.info("Sent connection information")
  }

  def getConnectionInformations() = {
    val request = DummyText("Send me connection information")
    blockingStub.sendConnectionInformation(request)
  }

  def sendKeyRange(min: String, max: String): Unit = {
    // logger.info("Sending key range ...")
    val request = KeyRange(minKey = min, maxKey = max)
    val response = blockingStub.determineKeyRange(request)
    // logger.info(response.dummyText)
    print()
  }

  def askIfDonePartitioning(): Boolean = {
    val request = DummyText(dummyText = "Are you done partitioning?")
    val response = blockingStub.isDonePartitioning(request)
    // logger.info(response.dummyText)
    if(response.dummyText == "Received all key ranges") {
      return true
    } else {
      return false
    }
  }

  def getPartitions(): Unit = {
    logger.info("Asking for partitions")
    val request = DummyText(dummyText = "Can I get partitions plz? :)")
    val response = blockingStub.sendPartitionedValues(request)
    allPartitions = response.partitions
    globalMaxKey = response.globalMax
    noWorkers = allPartitions.size
  }

  def makeServer() = {
    val executionContext = ExecutionContext.global
    workerServer = ServerBuilder.forPort(port)
                    .addService(WorkerConnectionsGrpc.bindService(new WorkerConnectionImpl(self.id), executionContext))
                    .build.start
  }

  def makeStubs(connectionInfo: ConnectionInformations) = {
    for {
      information <- connectionInfo.information
      host = information.host
      port = information.port
      id2 = information.id
      if(id2 != id)
      channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build
    } yield (WorkerConnectionsGrpc.blockingStub(channel), id2)
  }

  def getPartition(partitionID: Int) = {
    val filename = outputDirectory+"/partition"+id.toString+"."+partitionID
    try {
      val dataList = Source.fromFile(filename).getLines.toList
      val dataSeq = for {
                      dataLine <- dataList
                      dataValues = dataLine.split(" ", 2)
                    } yield (Data(key = dataValues(0), value = dataValues(1)))
      val reply = Dataset(data = dataSeq, partitionID = partitionID, fromUserID = self.id)
      new File(filename).delete()
      reply
    } catch {
      // Partition doesn't exist
      case e: FileNotFoundException => Dataset()
    }
  }


  private class WorkerConnectionImpl(id: Int) extends WorkerConnectionsGrpc.WorkerConnections {
    override def getWantedPartitions(req: Dataset) = {
      val filename = outputDirectory+"/partition"+req.fromUserID.toString+"."+req.partitionID
      val partition = new File(filename)
      val printWriter: PrintWriter = new PrintWriter(new FileWriter(partition, true));

      if(!partition.exists()){
        partition.createNewFile()
      }

      for {
        data <- req.data
      } yield (printWriter.append(data.key + " " + data.value + "\n"))

      printWriter.close();
      Future.successful(DummyText("Got partitions"))
    }
  }
}