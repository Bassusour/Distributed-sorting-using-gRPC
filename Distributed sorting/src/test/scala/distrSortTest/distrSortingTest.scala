package distrSortTest

import org.scalatest.FunSuite

class HelloTests extends FunSuite {
  import scala.concurrent.{ExecutionContext, Future, Await}
  import protoDistrSorting.distrSort.{DistrSortingGrpc, ID, KeyRange, DummyText, PartitionedValues, Partition, Dataset, Data, ConnectionInformation}

  // test("Client is assigned correct ID") {
  //   val server = new Master(ExecutionContext.global, 1)
  //   server.start
  //   val client = Worker("127.0.0.1", 50051)
  //   client.getID()
  //   server.stop()
  //   assert(client.id == 0)
  // }

  // test("Client receives correct partition") {
  //   val server = new Master(ExecutionContext.global, 1)
  //   server.start
  //   val client = Worker("localhost", 50051)
  //   client.sendKeyRange("abc", "klm")
  //   client.getPartitions()
  //   server.stop()
  //   assert(client.myPartition == Partition("a","k"))
  // }

  
}