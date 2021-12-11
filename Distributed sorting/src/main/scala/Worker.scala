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


import java.util.logging.Logger
import scala.io.StdIn

import io.grpc.Grpc

import io.grpc.ServerInterceptors;
import io.grpc.stub.StreamObserver;

// import protoDistrSorting.distrSort.{DistrSortingGVInformation, ConnectionInformations}
import scala.language.postfixOps
import scala.concurrent.{ExecutionContext, Future, Await}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Promise
import scala.concurrent.duration.Duration
import java.io._
import scala.io.Source

// Companion object
object Worker {
  // Constructor
  def apply(): Worker = {
    val channel = ManagedChannelBuilder.forAddress("127.0.0.1", 50051).usePlaintext().build
    val blockingStub = DistrSortingGrpc.blockingStub(channel)
    
    // println(channel);
    // println(blockingStub);
    new Worker(channel, blockingStub)
  }

  def main(args: Array[String]): Unit = {
    val client = Worker()
    var sentPartitionsCounter = 0
    try {
      // Generate data here - Edwige

      client.getID()
      client.sendConnectionInformation()
      val sorting = new sorting()
      sorting.generateData("data" + client.id)

      // Local sort here - Edwige
      // var locallySorted = sorting.toList
      // locallySorted = locallySorted.sorted


      // Get min and max key here - Edwige
      // val min = locallySorted.head
      // val max = locallySorted.last
      client.sendKeyRange("abc", "jkl")
      while(!client.askIfDonePartitioning()) {
        Thread.sleep(1000)
      }
      
      client.getPartitions()

      val ci = client.getConnectionInformations

      client.makeServer()

      val workerStubs = client.makeStubs(ci)

      // client.sendUnwantedPartitions(workerStubs)

      // while(sentPartitionsCounter < client.noWorkers) {
      //   if(sentPartitionsCounter != client.id){
      //     client.sendUnwantedPartition("src/main/scala/testFile."+sentPartitionsCounter.toString) //"partition."+client.sentPartitionsCounter
      //     sentPartitionsCounter = sentPartitionsCounter+1
      //   }
      //   client.getWantedPartition()
      // } 

    } finally {
      client.shutdown()
    }
  }
}

class Worker private(
  private val channel: ManagedChannel,
  private val blockingStub: DistrSortingBlockingStub
) { self =>
  var id: Int = 0;
  var myPartition: Partition = Partition("","")
  private var allPartitions: Seq[Partition] = Seq()
  private var noWorkers: Int = 0
  private var host: String = ""
  private var port: Int = 0
  private var workerServer: Server = null
  // private var workerChannel: ManagedChannelBuilder = null
  // private var workerStub: DistrSortingGrpc = null
  // private var sentPartitionsCounter = 0
  private[this] val logger = Logger.getLogger(classOf[Worker].getName)

  def shutdown(): Unit = {
    channel.shutdown.awaitTermination(5, TimeUnit.SECONDS)
  }

  def getID(): Unit = {
    val request = DummyText(dummyText = "Give me an ID")
    val response = blockingStub.assignID(request)
    logger.info("ID: " + response.id)
    id = response.id;
    host = "127.0.0.1"
    port = 50052+id
  }

  def sendConnectionInformation(): Unit = {
    val request = ConnectionInformation(host = host, port = port, id = id)
    val response = blockingStub.getConnectionInformation(request)
    logger.info("Sent connection information")
  }

  def getConnectionInformations() = {
    val request = DummyText("Send me connection information")
    blockingStub.sendConnectionInformation(request)
  }

  def sendKeyRange(min: String, max: String): Unit = {
    logger.info("Sending key range ...")
    val request = KeyRange(minKey = min, maxKey = max)
    val response = blockingStub.determineKeyRange(request)
    logger.info(response.dummyText)
  }

  def askIfDonePartitioning(): Boolean = {
    val request = DummyText(dummyText = "Are you done partitioning?")
    val response = blockingStub.isDonePartitioning(request)
    logger.info(response.dummyText)
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
    myPartition = allPartitions(id)
    noWorkers = allPartitions.size
    logger.info("Seq: " + response.partitions + " noWorkers: " + noWorkers)
  }

  def makeServer() = {
    val executionContext = ExecutionContext.global
    workerServer = ServerBuilder.forPort(port)
                    .addService(WorkerConnectionsGrpc.bindService(new WorkerConnectionImpl(id), executionContext)).build.start
  }

  def makeStubs(connectionInfo: ConnectionInformations) = {
    for {
      information <- connectionInfo.information
      host = information.host
      port = information.port
      id2 = information.id
      if(id2 != id)
      channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build
    } yield (DistrSortingGrpc.blockingStub(channel))
    // workerChannel = ManagedChannelBuilder.forAddress("127.0.0.1", 50051).usePlaintext().build
    // workerStub = DistrSortingGrpc.blockingStub(channel)
  }

  // def sendUnwantedPartitions(stubs: Seq[Stub]) = {

  // }


  private class WorkerConnectionImpl(id: Int) extends WorkerConnectionsGrpc.WorkerConnections {

    override def getUnwantedPartitions(req: Dataset) = {
      val filename = "clientPartitions/client"+id+"/partition."+req.partitionID
      val partition = new File(filename)
      val printWriter: PrintWriter = new PrintWriter(new FileWriter(partition, true));

      if(!partition.exists()){ 
        partition.createNewFile()
      } 

      for {
        data <- req.data
      } yield (printWriter.append(data.key + " " + data.value + "\n"))

      printWriter.close();
      Future.successful(DummyText("Got unwanted data"))
    }
    
  }



  // def sendUnwantedPartition(filename: String): Unit = {
  //   val dataList = Source.fromFile(filename).getLines.toList
  //   val dataSeq = for {
  //                   dataLine <- dataList
  //                   dataValues = dataLine.split(" ", 2)
  //                 } yield (Data(key = dataValues(0), value = dataValues(1)))
  //   val partitionID = filename takeRight 1 // filename takeRight 1
  //   val request = Dataset(data = dataSeq, partitionID = partitionID.toInt)
  //   val response = blockingStub.getUnwantedPartitions(request)
  //   print("asdasd")
  // }

  // def getWantedPartition(): Unit = {
  //   val request = ID(id = id)
  //   val response = blockingStub.sendWantedPartitions(request)
  //   val wantedPartitions = new File("clientFiles/wantedPartitions"+id.toString+".txt")
  //   val printWriter: PrintWriter = new PrintWriter(new FileWriter(wantedPartitions, true));

  //   if(!wantedPartitions.exists()){  //Files.exists(Paths.get(filename))
  //     wantedPartitions.createNewFile()
  //   } 

  //   for {
  //     data <- response.data
  //   } yield (printWriter.append(data.key + " " + data.value + "\n"))
    
  //   printWriter.close();
  // }

  // def askIfDoneReceivingPartitions(): Boolean = {
  //   val request = DummyText(dummyText = "Are you done receiving partitions?")
  //   val response = blockingStub.isDoneReceivingPartitions(request)
  //   logger.info(response.dummyText)
  //   if(response.dummyText == "Received all partitions") {
  //     return true
  //   } else {
  //     return askIfDoneReceivingPartitions()
  //   }
  // }

  // def askIfAllWorkersAreReady(): Boolean = {
  //   val request = DummyText(dummyText = "Have all workers received the go signal?")
  //   val response = blockingStub.waitForAllWorkers(request)
  //   logger.info(response.dummyText)
  //   if(response.dummyText == "All workers received the go signal") {
  //     return true
  //   } else {
  //     return askIfAllWorkersAreReady()
  //   }
  // }

  // def resetCounter() = {
  //   val request = DummyText(dummyText = "Reset counters")
  //   val response = blockingStub.resetCounters(request) 
  // }
}