package distrSortTest

import java.time._
import java.util.concurrent.TimeUnit
import java.util.logging.{Level, Logger}
import protoDistrSorting.distrSort.{DistrSortingGrpc, ID, KeyRange, DummyText, PartitionedValues, Partition, Dataset, Data, ConnectionInformation}
import protoDistrSorting.distrSort.DistrSortingGrpc.DistrSortingBlockingStub
import io.grpc.{StatusRuntimeException, ManagedChannelBuilder, ManagedChannel}
import scala.io.Source
import java.io._
import scala.concurrent.{Await, ExecutionContext, Future, Promise}
import scala.concurrent.duration.Duration
import ExecutionContext.Implicits.global

// Companion object
object Worker {
  // Constructor
  def apply(host: String, port: Int): Worker = {
    val channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build
    val blockingStub = DistrSortingGrpc.blockingStub(channel)
    
    println(channel);
    println(blockingStub);
    new Worker(channel, blockingStub, host, port)
  }

  def main(args: Array[String]): Unit = {
    val client = Worker("localhost", 50051)
    var sentPartitionsCounter = 0
    try {
      // Generate data here - Edwige

      // Local sort here - Edwige

      client.getID()

      // Get min and max key here - Edwige

      client.sendKeyRange("abc", "klm")
      while(!client.askIfDonePartitioning()) {
        Thread.sleep(1000)
      }
      
      client.getPartitions()

      while(sentPartitionsCounter < client.noWorkers) {
        if(sentPartitionsCounter != client.id){
          client.sendUnwantedPartition("src/main/scala/testFile."+sentPartitionsCounter.toString) //"partition."+client.sentPartitionsCounter
          sentPartitionsCounter = sentPartitionsCounter+1
        }
        client.getWantedPartition()
      } 

      // Split keys into partitions here (and sort them) - Edwige

      // Sends single unwanted partition, and receives single wanted partition
      // Until only wanted partitions are left
      // while(sentPartitionsCounter < client.noWorkers) {
      //   // if(client.sentPartitionsCounter != client.id){
      //     // println(client.sentPartitionsC)
      //   client.sendUnwantedPartition("src/main/scala/testFile."+sentPartitionsCounter.toString) //"partition."+client.sentPartitionsCounter
      //   sentPartitionsCounter = sentPartitionsCounter+1
      //   // }
      //   while(!client.askIfDoneReceivingPartitions) {
      //     Thread.sleep(500)
      //   }
      //   Thread.sleep(500)
      //   while(!client.askIfAllWorkersAreReady){
      //     // All workers will be here at the same time
      //     Thread.sleep(500)
      //   }

      //   Thread.sleep(1000)
      //   client.getWantedPartition()
      //   client.resetCounter
      // }

    } finally {
      client.shutdown()
    }
  }
}

class Worker private(
  private val channel: ManagedChannel,
  private val blockingStub: DistrSortingBlockingStub,
  private val host: String,
  private val port: Int
) {
  var id: Int = 0;
  var myPartition: Partition = Partition("","")
  private[this] var allPartitions: Seq[Partition] = Seq()
  private var noWorkers: Int = 0
  // private var sentPartitionsCounter = 0
  private[this] val logger = Logger.getLogger(classOf[Worker].getName)

  def shutdown(): Unit = {
    channel.shutdown.awaitTermination(5, TimeUnit.SECONDS)
  }

  def getID(): Unit = {
    val request = ConnectionInformation(host = host, port = port)
    val response = blockingStub.assignID(request)
    logger.info("ID: " + response.id)
    id = response.id;
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

  def sendUnwantedPartition(filename: String): Unit = {
    val dataList = Source.fromFile(filename).getLines.toList
    val dataSeq = for {
                    dataLine <- dataList
                    dataValues = dataLine.split(" ", 2)
                  } yield (Data(key = dataValues(0), value = dataValues(1)))
    val partitionID = filename takeRight 1 // filename takeRight 1
    val request = Dataset(data = dataSeq, partitionID = partitionID.toInt)
    val response = blockingStub.getUnwantedPartitions(request)
    print("asdasd")
  }

  def getWantedPartition(): Unit = {
    val request = ID(id = id)
    val response = blockingStub.sendWantedPartitions(request)
    val wantedPartitions = new File("clientFiles/wantedPartitions"+id.toString+".txt")
    val printWriter: PrintWriter = new PrintWriter(new FileWriter(wantedPartitions, true));

    if(!wantedPartitions.exists()){  //Files.exists(Paths.get(filename))
      wantedPartitions.createNewFile()
    } 

    for {
      data <- response.data
    } yield (printWriter.append(data.key + " " + data.value + "\n"))
    
    printWriter.close();
  }

  def askIfDoneReceivingPartitions(): Boolean = {
    val request = DummyText(dummyText = "Are you done receiving partitions?")
    val response = blockingStub.isDoneReceivingPartitions(request)
    logger.info(response.dummyText)
    if(response.dummyText == "Received all partitions") {
      return true
    } else {
      return askIfDoneReceivingPartitions()
    }
  }

  def askIfAllWorkersAreReady(): Boolean = {
    val request = DummyText(dummyText = "Have all workers received the go signal?")
    val response = blockingStub.waitForAllWorkers(request)
    logger.info(response.dummyText)
    if(response.dummyText == "All workers received the go signal") {
      return true
    } else {
      return askIfAllWorkersAreReady()
    }
  }

  def resetCounter() = {
    val request = DummyText(dummyText = "Reset counters")
    val response = blockingStub.resetCounters(request) 
  }
}