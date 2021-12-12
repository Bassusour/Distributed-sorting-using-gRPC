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
    server.blockUntilShutdown()
  }

  private val port = 45012
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
                .build.start
    Master.logger.info("127.0.0.1:45012")
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
      def defineRanges(min: String, max: String, numberSlaves: Int): List[Partition] = {
        val minChar = min.toCharArray
        val maxChar = max.toCharArray
        val minInt = new Array[Int](10)
        val maxInt = new Array[Int](10)
        val interval = new Array[Int](10)

        for (ind <- 0 to 9){
          minInt(ind) = minChar(ind).toInt
          maxInt(ind) = maxChar(ind).toInt
          interval(ind) = (maxInt(ind) - minInt(ind)).abs / numberSlaves
        }

        var ranges: List[Partition] = List(Partition(min.take(10)))
        for (indRange <- 1 until numberSlaves){
          var string = ""
          for (indChar <- List(0,1,2,3,4,5,6,7,8,9)) {
            var char = (minInt(indChar) + indRange*interval(indChar)).toChar
            if (char > 126) char = 126
            string = string + char
          }
          ranges = Partition(string) :: ranges
        }
        (Partition(max.take(10)) :: ranges).reverse
      }

      var partitions = defineRanges(globalMinKey, globalMaxKey, noWorkers)
      val reply = PartitionedValues(partitions = partitions, globalMax = globalMaxKey)
      // val reply = PartitionedValues(Seq(Partition("a")))
      Future.successful(reply)
    }
  }
}