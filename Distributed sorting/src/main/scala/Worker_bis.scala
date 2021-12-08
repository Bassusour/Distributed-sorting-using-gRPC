import io.grpc.{ManagedChannel, ManagedChannelBuilder}
import protoDistrSorting.distrSort.DistrSortingGrpc.DistrSortingBlockingStub
import protoDistrSorting.distrSort._

import java.util.concurrent.TimeUnit
import java.util.logging.Logger
import scala.io.Source

// Companion object
object Worker {
  // Constructor
  def apply(host: String, port: Int): Worker = {
    val channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build
    val blockingStub = DistrSortingGrpc.blockingStub(channel)
    
    println(channel);
    println(blockingStub);
    new Worker(channel, blockingStub)
  }

  def main(args: Array[String]): Unit = {
    val client = Worker("localhost", 50051)
    try {

      // Generate testFile here - Edwige
      val toSort = new sorting()
      toSort.generateData("data" + client.getID())

      // Local sort here - Edwige
      var locallySorted = toSort.toList
      locallySorted = locallySorted.sorted

      client.getID()

      // Get min and max key here - Edwige
      val min = locallySorted.head
      val max = locallySorted.last
      client.sendKeyRange(min, max)
      while(!client.askIfDonePartitioning()) {
        Thread.sleep(1000)
      }
      
      client.getPartitions()

      // Split keys into partitions here (and sort them) - Edwige


      // Sends single unwanted partition, and receives single wanted partition
      // Until only wanted partitions are left
      // while(client.sentPartitionsCounter != client.noWorkers-1) {
        client.sendUnwantedPartition("src/main/scala/testFile2.txt") //"Partition"+client.sentPartitionsCounter+".txt"

        while(!client.askIfDoneReceivingPartitions) {
          Thread.sleep(1000)
        }
        while(!client.askIfAllWorkersAreReady){
          // All workers will be here at the same time
          Thread.sleep(1000)
        }
        // Receive wanted partition from master here - Bastian
        // client.getWantedPartition()

      // }

      // Merge the testFile into one - Edwige

    } finally {
      client.shutdown()
    }
  }
}

class Worker private(
  private val channel: ManagedChannel,
  private val blockingStub: DistrSortingBlockingStub
) {
  private[this] var id: Int = 0;
  private[this] var myPartition: Partition = Partition("","")
  private[this] var allPartitions: Seq[Partition] = Seq()
  private var noWorkers: Int = 0
  private var sentPartitionsCounter = 0
  private[this] val logger = Logger.getLogger(classOf[Worker].getName)

  def shutdown(): Unit = {
    channel.shutdown.awaitTermination(5, TimeUnit.SECONDS)
  }

  def getID(): Int = {
    val request = DummyText(dummyText = "heyo")
    val response = blockingStub.assignID(request)
    logger.info("ID: " + response.id)
    id = response.id;
    id
  }

  def sendKeyRange(min: String, max: String): Unit = {
    logger.info("Sending key range ...")
    val request = KeyRange(minKey = min, maxKey = max)
    val response = blockingStub.determineKeyRange(request)
    logger.info(response.dummyText)
  }

  def askIfDonePartitioning(): Boolean = {
    logger.info("Sending key range ...")
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
    val partitionID = 1 // filename takeRight 1
    val request = Dataset(data = dataSeq, partitionID = partitionID)
    val response = blockingStub.getUnwantedPartitions(request)
    sentPartitionsCounter += 1
    print("asdasd")
  }

  def getWantedPartition(): Unit = {
    val request = ID(id = id)
    val response = blockingStub.sendWantedPartitions(request)

    
  }

  def askIfDoneReceivingPartitions(): Boolean = {
    val request = DummyText(dummyText = "Are you done receiving partitions?")
    val response = blockingStub.isDoneReceivingPartitions(request)
    logger.info(response.dummyText)
    if(response.dummyText == "Received all partitions") {
      return true
    } else {
      return false
    }
  }

  def askIfAllWorkersAreReady(): Boolean = {
    val request = DummyText(dummyText = "Have all workers received the go signal?")
    val response = blockingStub.waitForAllWorkers(request)
    logger.info(response.dummyText)
    if(response.dummyText == "All workers received the go signal") {
      return true
    } else {
      return false
    }
  }
}