import java.io.{BufferedWriter, File, FileWriter}
import scala.io.Source
import scala.language.postfixOps
import scala.sys.process._

class sorting {

  def generateData(fileName : String): Unit = {
    "gensort -a 10 " + fileName !!
  }

  def isBefore(stringCurrent: String, stringUpper: String, length : Int): Boolean = {
    if(stringCurrent.head < stringUpper.head) true
    else if(stringCurrent.head > stringUpper.head) false
    else if(length == 1) true
    else isBefore(stringCurrent.tail, stringUpper.tail, length -1)
  }

  def isAfter(stringCurrent: String, stringLower: String, length : Int): Boolean = {
    if(stringCurrent.head > stringLower.head) true
    else if(stringCurrent.head < stringLower.head) false
    else if(length == 1) false
    else isAfter(stringCurrent.tail, stringLower. tail, length -1)
  }

  def inRange(stringCurrent: String, stringLower: String, stringUpper: String): Boolean = {
    isBefore(stringCurrent, stringUpper, 10) && isAfter(stringCurrent, stringLower, 10)
  }

  def toList: List[String] = {
    val list = Source.fromFile("data").getLines.toList
    Source.fromFile("data").close
    println(new File(".\\data").delete())
    list
  }

  def partition (list: List[String], beginning: String, end: String ): List[String] = {
    list.filter(inRange(_, beginning, end))
  }

  def separatePartition(allPartitions : Seq[Partition], notPartitioned : List[String]) : Seq[List[String]] = {
    var separatedList: Seq[List[String]] = Seq()
    for(i <- 0 to allPartitions.length-2) {
      val partition = this.partition(notPartitioned, allPartitions.apply(i).val, allPartitions.apply(i+1).val)
      separatedList = separatedList:+ partition
    }
    separatedList
  }

  def writeInFile(keys : List[String], range : Int) : Unit ={
    val file = "range" + range
    val bw = new BufferedWriter(new FileWriter(file))
    for (line <- keys) {
      bw.write(line + "\n")
    }
    bw.close()
  }
}

object Main extends App {
  val sort = new sorting()
  /*sort.generateData("data")
  var toSort = sort.toList
  toSort = toSort.sorted
  println(toSort)
  val ranges = Seq("!!!!!!!!!!", "T~:]~M?eKB", "~~~~~~~~~~")
  println(sort.separatePartition(ranges, toSort))*/
}