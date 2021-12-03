// package io.grpc.examples.helloworld

import java.time._
import java.util.concurrent.TimeUnit
import java.util.logging.{Level, Logger}
import protoGreet.hello.{GreeterGrpc, GreeterRequest, ID, KeyRange, DummyText, PartitionedValues, Partition}
import protoGreet.hello.GreeterGrpc.GreeterBlockingStub
import io.grpc.{StatusRuntimeException, ManagedChannelBuilder, ManagedChannel}

// Companion object
object Worker {
  // Constructor
  def apply(host: String, port: Int): Worker = {
    val channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build
    val blockingStub = GreeterGrpc.blockingStub(channel)
    
    println(channel);
    println(blockingStub);
    new Worker(channel, blockingStub)
  }

  def main(args: Array[String]): Unit = {
    val client = Worker("localhost", 50051)
    try {
      val user = "Bastian"
      client.greet(user)
      client.sendKeyRange("abc", "klm")

      while(!client.askIfDonePartitioning()) {
        Thread.sleep(1000)
      }
      
      client.getPartitions()

    } finally {
      client.shutdown()
    }
  }
}

class Worker private(
  private val channel: ManagedChannel,
  private val blockingStub: GreeterBlockingStub
) {
  private[this] var id: Int = 0;
  private[this] var myPartition: Partition = Partition("","")
  private[this] var allPartitions: Seq[Partition] = Seq()
  private[this] var noWorkers: Int = 0
  private[this] val logger = Logger.getLogger(classOf[Worker].getName)

  def shutdown(): Unit = {
    channel.shutdown.awaitTermination(5, TimeUnit.SECONDS)
  }

  def greet(greeting: String): Unit = {
    logger.info("Will try to greet as " + greeting + " ...")
    val request = GreeterRequest(greeting = greeting)
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
}

/*
try {
      // Server response
      val response = blockingStub.assignID(request)
      logger.info("ID: " + response.id)
      id = response.id;
    }
    catch {
      case e: StatusRuntimeException =>
        logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus)
    }
    */