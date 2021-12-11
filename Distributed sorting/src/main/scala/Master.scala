package distrSortTest
import java.util.logging.Logger
import scala.io.StdIn

import io.grpc.Grpc
import io.grpc.{Server, ServerBuilder}

import io.grpc.ServerInterceptors;
import io.grpc.stub.StreamObserver;

import protoDistrSorting.distrSort.{DistrSortingGrpc, ID, KeyRange, DummyText, PartitionedValues, Partition, 
                                    Dataset, Data, ConnectionInformation, ConnectionInformations}
import scala.language.postfixOps
import scala.concurrent.{ExecutionContext, Future, Await}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Promise
import scala.concurrent.duration.Duration
import java.io._
import scala.io.Source

// Companion object
object Master {
  private val logger = Logger.getLogger(classOf[Master].getName)

  def main(args: Array[String]): Unit = {
    val noWorkers = args(0).toInt
    val server = new Master(ExecutionContext.global, noWorkers)
    server.start()
    // println(server.getAddress)
    server.blockUntilShutdown()
  }

  private val port = 50051
}

class Master(executionContext: ExecutionContext, noWorkers: Int) { self =>
  private[this] var server: Server = null
  private[this] var shouldReset: Boolean = true
  private var workerID: Int = 0;
  private var globalMinKey = "~"
  private var globalMaxKey = " "
  private var noOfReceivedKeyRanges = 0
  private var noReceivedPartitions = 0
  private var replyCounter = 0
  private var listOfAddresses = List.empty[(String,Int, Int)]  //host port id

  def start(): Unit = {
    server = ServerBuilder.forPort(Master.port)
                .addService(DistrSortingGrpc.bindService(new DistrSortingImpl, executionContext))
                // .intercept(new IPInterceptor())
                .build.start
    Master.logger.info("127.0.0.1:50051")
    sys.addShutdownHook {
      System.err.println("*** shutting down gRPC server since JVM is shutting down")
      self.stop()
      System.err.println("*** server shut down")
    }
  }

  def stop(): Unit = {
    if (server != null) {
      server.shutdown()
    }
  }

  private def blockUntilShutdown(): Unit = {
    if (server != null) {
      server.awaitTermination()
    }
  }

  private class DistrSortingImpl extends DistrSortingGrpc.DistrSorting {
    
    override def assignID(req: DummyText) = {
      val reply = ID(id = workerID)
      workerID += 1;
      Future.successful(reply)
    }

    // override def getIPaddress(req: )
    override def getConnectionInformation(req: ConnectionInformation) = {
      println("Client joined: " + req.host + ":" + req.port + " ID:" + req.id)
      listOfAddresses = listOfAddresses :+ (req.host, req.port, req.id)
      val reply = DummyText(dummyText = "Received information")
      Future.successful(reply)
    }

    override def sendConnectionInformation(req: DummyText) = {
      val connectionInformation = for {
                                    (host, port, id) <- listOfAddresses
                                  } yield(ConnectionInformation(host, port, id))
      val reply = ConnectionInformations(connectionInformation)
      Future.successful(reply)
    }

    override def determineKeyRange(req: KeyRange) = {
      val min = req.minKey
      val max = req.maxKey
      if(min < globalMinKey) {
        globalMinKey = min
      }
      if( max > globalMaxKey) {
        globalMaxKey = max
      }
      noOfReceivedKeyRanges += 1
      val reply = DummyText(dummyText = "Received key range")
      Future.successful(reply)
      }

    override def isDonePartitioning(req: DummyText) = {
      if(noOfReceivedKeyRanges != noWorkers) {
        val reply = DummyText(dummyText = "Still waiting for more data")
        Future.successful(reply)
      } else {
        val reply = DummyText(dummyText = "Received all key ranges")
        Future.successful(reply)
      }
    }

    override def sendPartitionedValues(req: DummyText) = {
      // def defineRanges(min: String, max: String, numberSlaves: Int): List[Partition] = {
      //   val minChar = min.toCharArray
      //   val maxChar = max.toCharArray
      //   val minInt = new Array[Int](10)
      //   val maxInt = new Array[Int](10)
      //   val interval = new Array[Int](10)

      //   for (ind <- 0 to 9){
      //     minInt(ind) = minChar(ind).toInt
      //     maxInt(ind) = maxChar(ind).toInt
      //     interval(ind) = (maxInt(ind) - minInt(ind)).abs / numberSlaves
      //   }

      //   var ranges: List[String] = List(Partition(min))
      //   for (indRange <- 1 until numberSlaves){
      //     var string = ""
      //     for (indChar <- List(0,1,2,3,4,5,6,7,8,9)) {
      //       var char = (minInt(indChar) + indRange*interval(indChar)).toChar
      //       if (char > 126) char = 126
      //       string = string + char
      //     }
      //     ranges = Partition(string) :: ranges
      //   }
      //   (Partition(max) :: ranges).sorted
      // }

      // var partitions = defineRanges(globalMinKey, globalMaxKey, noWorkers

      // val reply = PartitionedValues(partitions = partitions)
      val reply = PartitionedValues(Seq(Partition("a","b")))
      Future.successful(reply)
    }

    // override def getUnwantedPartitions(req: Dataset) = {
    //   saves data according to the id
    //   Future.successful(Text("Got unwanted data"))
    // }

    // override def sendWantedPartitions(req: ID) = {
    //   wantedData = getData(req.id)
    //   Future.successful(Data(wantedData))
    // }

    // override def getUnwantedPartitions(req: Dataset) = {
    //   val filename = "serverPartitions/partition"+req.partitionID+".txt"
    //   val partition = new File(filename)
    //   val printWriter: PrintWriter = new PrintWriter(new FileWriter(partition, true));

    //   if(!partition.exists()){
    //     partition.createNewFile()
    //   }

    //   for {
    //     data <- req.data
    //   } yield (printWriter.append(data.key + " " + data.value + "\n"))

    //   printWriter.close();
    //   Future.successful(Text("Got unwanted data"))
    // }

    // override def sendWantedPartitions(req: ID) = {
    //   val filename = "serverPartitions/partition"+req.id+".txt"
    //   try {
    //     val dataList = Source.fromFile(filename).getLines.toList
    //     val dataSeq = for {
    //                     dataLine <- dataList
    //                     dataValues = dataLine.split(" ", 2)
    //                   } yield (Data(key = dataValues(0), value = dataValues(1)))
    //     val reply = Dataset(data = dataSeq, partitionID = req.id)
    //     new File(filename).delete()
    //     Future.successful(reply)
    //   } catch {
    //     // Partition doesn't exist
    //     case e: FileNotFoundException => Future.successful(Dataset())
    //   }
    // }

    // override def getUnwantedPartitions(req: Dataset) = {
    //   val filename = "serverPartitions/partition"+req.partitionID+".txt" 
    //   val partition = new File(filename)

    //   if(!partition.exists()){  //Files.exists(Paths.get(filename))
    //     partition.createNewFile()
    //   } 

    //   val printWriter: PrintWriter = new PrintWriter(new FileWriter(partition, true));

    //   for {
    //     data <- req.data
    //   } yield (printWriter.append(data.key + " " + data.value + "\n"))

    //   printWriter.close();
    //   noReceivedPartitions = noReceivedPartitions+1
    //   Future.successful(DummyText(dummyText = "Got unwanted data"))
    // }

    // override def sendWantedPartitions(req: ID) = {
    //   val filename = "serverPartitions/partition"+req.id+".txt"
    //   try {
    //     val dataList = Source.fromFile(filename).getLines.toList

    //     val dataSeq = for {
    //                     dataLine <- dataList
    //                     dataValues = dataLine.split(" ", 2)
    //                   } yield (Data(key = dataValues(0), value = dataValues(1)))
    //     val reply = Dataset(data = dataSeq, partitionID = req.id)
    //     new File(filename).delete()
    //     Future.successful(reply)
    //   } catch {
    //     // Partition doesn't exist
    //     case e: FileNotFoundException => Future.successful(Dataset())
    //   }
    // }

    // override def isDoneReceivingPartitions(req: DummyText) = {
    //   if(noReceivedPartitions != noWorkers-1) {
    //     val reply = DummyText(dummyText = "Still waiting for more partitions")
    //     Future.successful(reply)
    //   } else {
    //     val reply = DummyText(dummyText = "Received all partitions")
    //     replyCounter += 1
    //     Future.successful(reply)
    //   }
    // }

    // override def waitForAllWorkers(req: DummyText) = {
    //   var reply = DummyText()
    //   if(replyCounter != noWorkers) {
    //     reply = reply.withDummyText("Still waiting for all workers to receive the signal")
    //   } else {
    //     reply = reply.withDummyText("All workers received the go signal")
    //     // replyCounter = 0
    //     // noReceivedPartitions = 0
    //     shouldReset = true
    //   }
    //   Future.successful(reply)
    // }

    // override def resetCounters(req: DummyText) = {
    //   if(shouldReset){
    //     replyCounter = 0
    //     noReceivedPartitions = 0
    //   }
    //   shouldReset = false
    //   Future.successful(DummyText("Reset counters"))
    // }
  }
}