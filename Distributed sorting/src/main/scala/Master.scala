// package io.grpc.examples.helloworld

import java.util.logging.Logger
import scala.io.StdIn

import io.grpc.Grpc
import io.grpc.{Server, ServerBuilder}

import io.grpc.ServerInterceptors;
import io.grpc.stub.StreamObserver;

import protoGreet.hello.{GreeterGrpc, GreeterRequest, ID, KeyRange, DummyText, PartitionedValue, Partition}
import scala.language.postfixOps
import scala.concurrent.{ExecutionContext, Future, Await}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Promise
import scala.concurrent.duration.Duration
// import java.time.Duration
// import scala.compiletime.ops.boolean

// import com.example.grpc.Constant;

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
  private var noOfReceivedData = 0

  private def start(): Unit = {
    server = ServerBuilder.forPort(Master.port)
                .addService(GreeterGrpc.bindService(new GreeterImpl, executionContext))
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

  private class GreeterImpl extends GreeterGrpc.Greeter {
    
    override def assignID(req: GreeterRequest) = {
      workerID += 1;
      val reply = ID(id = workerID)
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
      noOfReceivedData += 1
      val reply = DummyText(dummyText = "Received key range")
      Future.successful(reply)
      }

    override def isDonePartitioning(req: DummyText) = {
      if(noOfReceivedData != noWorkers) {
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

      val arr = new Array[String](noWorkers)
      for( i <- 0 to arr.length - 1){
         arr(i) = ((i+1) * (maxnum-minNum)/noWorkers + minNum).toChar.toString
      }

      val reply = PartitionedValue(partitions = Seq(Partition(arr(0), arr(0))), 2)
      Future.successful(reply)

    }
  }
}