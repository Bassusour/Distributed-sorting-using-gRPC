import scala.io.Source
import scala.language.postfixOps
import scala.sys.process._

class sorting {

  def generateData(): Unit = {
    "gensort -a 10 data" !!
  }

  def isBefore(stringCurrent: String, stringUpper: String, ind: Int): Boolean = ind match{
    case 0 => false
    case _ =>
      if(stringCurrent.head <= stringUpper.head) true
      else if(stringCurrent.head > stringUpper.head) false
      else isBefore(stringCurrent.tail, stringUpper. tail, ind-1)
  }

  def isAfter(stringCurrent: String, stringLower: String, ind: Int): Boolean = ind match{
    case 0 => false
    case _ =>
      if(stringCurrent.head > stringLower.head) true
      else if(stringCurrent.head < stringLower.head) false
      else isBefore(stringCurrent.tail, stringLower. tail, ind-1)
  }

  def inRange(stringCurrent: String, stringLower: String, stringUpper: String): Boolean = {
    isBefore(stringCurrent, stringUpper, 10) && isAfter(stringCurrent, stringLower, 10)
  }

  def toList: List[String] = {
    Source.fromFile("data").getLines.toList
  }

  def partition (list: List[String], beginning: String, end: String ): List[String] = {
    list.filter(inRange(_, beginning, end))
  }

  def defineRanges(min: String, max: String, numberSlaves: Int): List[String] = {
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
  sort.generateData()
  var toSort = sort.toList
  toSort = toSort.sorted
  println(toSort)
  val ranges = sort.defineRanges(toSort.head, toSort.last, 2)
  println(ranges.head)
  println(sort.partition(toSort, toSort.head,ranges.head))
  println(sort.partition(toSort,ranges.head, toSort.last))
}