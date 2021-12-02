// package io.grpc.examples.helloworld

import java.util.logging.Logger
import scala.io.StdIn

import io.grpc.Grpc
import io.grpc.{Server, ServerBuilder}

import io.grpc.ServerInterceptors;
import io.grpc.stub.StreamObserver;

import protoGreet.hello.{GreeterGrpc, GreeterRequest, ID, KeyRange, DummyText}
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
    val numOfWorkers = StdIn.readInt()
    val server = new Master(ExecutionContext.global, numOfWorkers)
    server.start()
    server.blockUntilShutdown()
  }

  private val port = 50051
}

class Master(executionContext: ExecutionContext, numOfWorkers: Int) { self =>
  // JwtServerInterceptor jwtInterceptor = new JwtServerInterceptor(Constant.JWT_SECRET);
  private[this] var server: Server = null
  private var workerCount: Int = 0;
  private var globalMinKey = ""
  private var globalMaxKey = ""

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
      workerCount += 1;
      val reply = ID(id = workerCount)
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

      val reply = DummyText(dummyText = "Received key range")
      Future.successful(reply)
      }
    }
}