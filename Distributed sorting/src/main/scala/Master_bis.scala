import io.grpc.{Server, ServerBuilder}
import protoDistrSorting.distrSort._

import java.io._
import java.util.logging.Logger
import scala.concurrent.{ExecutionContext, Future}
import scala.io.{Source, StdIn}
import scala.language.postfixOps

// Companion object
object Master {
  private val logger = Logger.getLogger(classOf[Master].getName)

  def main(args: Array[String]): Unit = {
    print("Enter number of workers (int): ")
    val noWorkers = StdIn.readInt()
    val server = new Master(ExecutionContext.global, noWorkers)
    server.start()
    server.blockUntilShutdown()
  }

  private val port = 50051
}

class Master(executionContext: ExecutionContext, noWorkers: Int) { self =>
  // JwtServerInterceptor jwtInterceptor = new JwtServerInterceptor(Constant.JWT_SECRET);
  private[this] var server: Server = null
  private var workerID: Int = 0;
  private var globalMinKey = "~"
  private var globalMaxKey = " "
  private var noOfReceivedKeyRanges = 0
  private var noReceivedPartitions = 0
  private var replyCounter = 0

  private def start(): Unit = {
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

  private def stop(): Unit = {
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
        val reply = DummyText(dummyText = "Still waiting for more testFile")
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

    override def getUnwantedPartitions(req: Dataset) = {
      val filename = "partitions/partition"+req.partitionID+".txt" 
      val partition = new File(filename)

      if(!partition.exists()){  //Files.exists(Paths.get(filename))
        partition.createNewFile()
      } 

      val printWriter: PrintWriter = new PrintWriter(new FileWriter(partition, true));

      for {
        data <- req.data
      } yield (printWriter.append(data.key + " " + data.value + "\n"))

      printWriter.close();
      noReceivedPartitions += 1
      Future.successful(DummyText(dummyText = "Got unwanted testFile"))
    }

    override def sendWantedPartitions(req: ID) = {
      val filename = "partitions/partition"+req.id+".txt"
      try {
        val dataList = Source.fromFile(filename).getLines.toList
      } catch {
        // file doesn't exist
        return Future.successful(Data())
      }

      val dataSeq = for {
                      dataLine <- dataList
                      dataValues = dataLine.split(" ", 2)
                    } yield (Data(key = dataValues(0), value = dataValues(1)))
      val reply = Dataset(data = dataSeq, partitionID = req.id)
      Future.successful(reply)
    }

    override def isDoneReceivingPartitions(req: DummyText) = {
      if(noReceivedPartitions != noWorkers) {
        val reply = DummyText(dummyText = "Still waiting for more partitions")
        Future.successful(reply)
      } else {
        val reply = DummyText(dummyText = "Received all partitions")
        replyCounter += 1
        Future.successful(reply)
      }
    }

    override def waitForAllWorkers(rew: DummyText) = {
      if(replyCounter != noWorkers) {
        val reply = DummyText(dummyText = "Still waiting for all workers to receive the go signal")
      } else {
        val reply = DummyText(dummyText = "All workers received the go signal")
        replyCounter = 0
        noReceivedPartitions = 0
      }
      Future.successful(reply)
    }
  }
}