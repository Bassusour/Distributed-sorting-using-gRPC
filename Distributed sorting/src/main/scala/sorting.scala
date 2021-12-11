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

  def defineRanges(min: String, max: String, numberSlaves: Int): Seq[String] = {
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

    var ranges: List[String] = List()
    for (indRange <- 1 until numberSlaves){
      var string = ""
      for (indChar <- List(0,1,2,3,4,5,6,7,8,9)) {
        var char = (minInt(indChar) + indRange*interval(indChar)).toChar
        if (char > 126) char = 126
        string = string + char
      }
      ranges = string :: ranges
    }
    ranges.sorted
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

  println(sort.defineRanges(""))
}