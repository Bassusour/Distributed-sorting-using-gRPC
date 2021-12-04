import java.time._
import java.util.concurrent.TimeUnit
import java.util.logging.{Level, Logger}
import protoDistrSorting.distrSort.{DistrSortingGrpc, ID, KeyRange, DummyText, PartitionedValues, Partition, Dataset, Data}
import protoDistrSorting.distrSort.DistrSortingGrpc.DistrSortingBlockingStub
import io.grpc.{StatusRuntimeException, ManagedChannelBuilder, ManagedChannel}
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

      // Generate data here - Edwige

      // Local sort here - Edwige

      client.getID()

      // Get min and max key here - Edwige

      client.sendKeyRange("abc", "klm")
      while(!client.askIfDonePartitioning()) {
        Thread.sleep(1000)
      }
      
      client.getPartitions()

      // Split keys into partitions here (and sort them) - Edwige

      // Send unwanted data to master here - Bastian
      client.sendUnwantedData("src/main/scala/testFile.txt")

      // Receive wanted data from master here - Bastian

      // Merge the data into one - Edwige

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
  private[this] var noWorkers: Int = 0
  private[this] val logger = Logger.getLogger(classOf[Worker].getName)

  def shutdown(): Unit = {
    channel.shutdown.awaitTermination(5, TimeUnit.SECONDS)
  }

  def getID(): Unit = {
    val request = DummyText(dummyText = "heyo")
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

  def sendUnwantedData(filename: String): Unit = {
    val dataList = Source.fromFile(filename).getLines.toList
    val dataSeq = for {
                    dataLine <- dataList
                    dataValues = dataLine.split(" ", 2)
                  } yield (Data(key = dataValues(0), value = dataValues(1)))
    val partitionID = 1 // filename takeRight 1
    val request = Dataset(data = dataSeq, partitionID = partitionID)
    val response = blockingStub.getUnwantedData(request)
    print("asdasd")
  }
}