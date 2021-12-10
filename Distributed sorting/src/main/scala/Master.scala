package distrSortTest
import java.util.logging.Logger
import scala.io.StdIn

import io.grpc.Grpc
import io.grpc.{Server, ServerBuilder}

import io.grpc.ServerInterceptors;
import io.grpc.stub.StreamObserver;

import protoDistrSorting.distrSort.{DistrSortingGrpc, ID, KeyRange, DummyText, PartitionedValues, Partition, Dataset, Data, ConnectionInformation}
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

  def start(): Unit = {
    server = ServerBuilder.forPort(Master.port)
                .addService(DistrSortingGrpc.bindService(new DistrSortingImpl, executionContext))
                // .intercept(new IPInterceptor())
                .build.start
    Master.logger.info("Server started " + server)
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
    
    override def assignID(req: ConnectionInformation) = {
      val reply = ID(id = workerID)
      workerID += 1;
      println("Client joined: " + req.host + req.port)
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
      val minNum = globalMinKey.charAt(0).toInt
      val maxnum = globalMaxKey.charAt(0).toInt

      val partitions = for{
                         i <- 0 to noWorkers-1
                         val min = (i * (maxnum-minNum)/noWorkers + minNum).toChar.toString
                         val max = ((i+1) * (maxnum-minNum)/noWorkers + minNum).toChar.toString
                       } yield (Partition(minVal = min, maxVal = max))

      val reply = PartitionedValues(partitions = partitions)
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

    override def getUnwantedPartitions(req: Dataset) = {
      val filename = "serverPartitions/partition"+req.partitionID+".txt" 
      val partition = new File(filename)

      if(!partition.exists()){  //Files.exists(Paths.get(filename))
        partition.createNewFile()
      } 

      val printWriter: PrintWriter = new PrintWriter(new FileWriter(partition, true));

      for {
        data <- req.data
      } yield (printWriter.append(data.key + " " + data.value + "\n"))

      printWriter.close();
      noReceivedPartitions = noReceivedPartitions+1
      Future.successful(DummyText(dummyText = "Got unwanted data"))
    }

    override def sendWantedPartitions(req: ID) = {
      val filename = "serverPartitions/partition"+req.id+".txt"
      try {
        val dataList = Source.fromFile(filename).getLines.toList

        val dataSeq = for {
                        dataLine <- dataList
                        dataValues = dataLine.split(" ", 2)
                      } yield (Data(key = dataValues(0), value = dataValues(1)))
        val reply = Dataset(data = dataSeq, partitionID = req.id)
        new File(filename).delete()
        Future.successful(reply)
      } catch {
        // Partition doesn't exist
        case e: FileNotFoundException => Future.successful(Dataset())
      }
    }

    override def isDoneReceivingPartitions(req: DummyText) = {
      if(noReceivedPartitions != noWorkers-1) {
        val reply = DummyText(dummyText = "Still waiting for more partitions")
        Future.successful(reply)
      } else {
        val reply = DummyText(dummyText = "Received all partitions")
        replyCounter += 1
        Future.successful(reply)
      }
    }

    override def waitForAllWorkers(req: DummyText) = {
      var reply = DummyText()
      if(replyCounter != noWorkers) {
        reply = reply.withDummyText("Still waiting for all workers to receive the signal")
      } else {
        reply = reply.withDummyText("All workers received the go signal")
        // replyCounter = 0
        // noReceivedPartitions = 0
        shouldReset = true
      }
      Future.successful(reply)
    }

    override def resetCounters(req: DummyText) = {
      if(shouldReset){
        replyCounter = 0
        noReceivedPartitions = 0
      }
      shouldReset = false
      Future.successful(DummyText("Reset counters"))
    }
  }
}