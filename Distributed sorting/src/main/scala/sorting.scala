package distrSortTest
import java.io.{BufferedWriter, File, FileWriter}
import scala.io.Source
import scala.language.postfixOps
import scala.sys.process._
import protoDistrSorting.distrSort.{DistrSortingGrpc, ID, KeyRange, DummyText, PartitionedValues, Partition, 
                                    Dataset, Data, ConnectionInformation, ConnectionInformations}

class sorting {
  def generateData(fileName : String, numberKeys : Int): Unit = {
    "gensort -a " + numberKeys + " " + fileName !!
  }

  def getData(inputDirectories : List[String]) : List[String] = {
    var keys : List[String] = List()
    inputDirectories.foreach( directory => {
      keys = keys ::: this.toList(directory)
    })
    keys
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

  def toList(fileName : String) : List[String] = {
    val list = Source.fromFile(fileName).getLines.toList
    Source.fromFile(fileName).close
    list
  }

  def partition (list: List[String], beginning: String, end: String ): List[String] = {
    list.filter(inRange(_, beginning, end))
  }

  def separatePartition(allPartitions : Seq[Partition], notPartitioned : List[String]) : Seq[List[String]] = {
    var separatedList: Seq[List[String]] = Seq()
    for(i <- 0 to allPartitions.length-2) {
      val partition = this.partition(notPartitioned, allPartitions.apply(i).value, allPartitions.apply(i+1).value)
      separatedList = separatedList:+ partition
    }
    separatedList
  }

  def getLocalKeys(partitionNumber : Int, noWorkers : Int) : List[String] = {
    var keys : List[String] = List()
    for(workerNumber <- 0 until noWorkers) {
      keys = "partition" + workerNumber + "." + partitionNumber :: keys
    }
    this.getData(keys)
  }

  def writeInFile(keys : List[String], fileName : String) : Unit ={
    val bw = new BufferedWriter(new FileWriter(fileName))
    for (line <- keys) {
      bw.write(line + "\n")
    }
    bw.close()
  }
}