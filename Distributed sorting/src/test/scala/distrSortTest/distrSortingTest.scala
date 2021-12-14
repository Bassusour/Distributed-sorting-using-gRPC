package distrSortTest

import org.scalatest.FunSuite

class HelloTests extends FunSuite {
  import scala.concurrent.{ExecutionContext, Future, Await}
  import protoDistrSorting.distrSort.{DistrSortingGrpc, ID, KeyRange, DummyText, PartitionedValues, Partition, Dataset, Data, ConnectionInformation}
  import java.io._

  test("Client is assigned correct ID") {
    val server = new Master(ExecutionContext.global, 1)
    server.start
    val client = Worker("127.0.0.1", 45012, "/outDir")
    client.getID()
    server.stop()
    assert(client.id == 0)
  }

  // test("Client receives correct partition") {
  //   val server = new Master(ExecutionContext.global, 1)
  //   server.start
  //   val client = Worker("127.0.0.1", 45012, "/outDir")
  //   client.getID()
  //   client.sendConnectionInformation()
  //   client.sendKeyRange("abc", "klm")
  //   while(!client.askIfDonePartitioning()) {
  //     Thread.sleep(1000)
  //   }
  //   client.getPartitions()
  //   server.stop()
  //   assert(client.allPartitions(client.id) == Partition("a"))
  // }

  // test("Generation data") {
  //   val sort = new sorting()
  //   val fileName = "testFile"
  //   sort.generateData(fileName, 10)
  //   val file = new File(fileName)
  //   assert(file.length != 0)
  //   file.delete
  // }

  test("string comparison ") {
    val sort = new sorting()
    assert(sort.isBefore("aaaaaaaaaa", "~~~~~~~~~~",10,  "", 0))
    assert(!sort.isAfter("aaaaaaaaaa", "~~~~~~~~~~",10,  "", 0))
    assert(sort.inRange("dhfteudjtà", "aaaaaaaaaa", "~~~~~~~~~~", "", 0))
  }

  test("partitioning") {
    val sort = new sorting()
    assert(!sort.partition(List("aaaaaaaaaaa", "ffffffffff", "kkkkkkkkkk", "tttttttttt", "uuuuuuuuuu", "wwwwwwwwww"), "aaaaaaaaaa", "wwwwwwwwww", "", 0).isEmpty)
  }

}