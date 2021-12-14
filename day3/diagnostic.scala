import java.io.{BufferedReader, FileReader}
import scala.annotation.tailrec
import scala.util.Using
// Gamma is bit-wise, most-common
// Sample is 10110 = 22
// Epsilon is bit-wise not of gamma
// Sample is 01001 = 9
// Power consumption = 22 x 9 = 198

val inputFile = "input.txt"
//val inputFile = "sample.txt"

val lines = Using.resource(BufferedReader(FileReader(inputFile))) { reader =>
  Iterator.continually(reader.readLine()).takeWhile(_ != null).toSeq
}

// Assumes good input - only '1' and '0' chars
val bitsByColumn = lines.map(_.toCharArray.map(_.toString.toInt)).transpose
val mostCommonBits = bitsByColumn.map(c => (c.sum.toDouble / c.length).round).map(_.toString()(0)).mkString
val gamma = BigInt(mostCommonBits, 2).toInt
val epsilon = ~gamma & BigInt("1"*mostCommonBits.length, 2).toInt

@tailrec
def filterForMostCommon(index: Int, lines: Array[Array[Int]]): Int = {
  //  println("Lines:\n  " + lines.map(_.mkString).mkString("\n  "))
  if lines.length == 1 then
    BigInt(lines.head.mkString, 2).toInt
  else
    val column = lines.transpose.apply(index)
    // Look at indexed column, find most common bit (if equal, keep '1')
    val mostCommonBit = (column.sum.toDouble / column.length).round
    // Filter input list starting with most common bit
    val matches = lines.filter(_ (index) == mostCommonBit)
    // Advance index pointer, repeat
    filterForMostCommon(index + 1, matches)
}

@tailrec
def filterForLeastCommon(index: Int, lines: Array[Array[Int]]): Int = {
  //  println("Lines:\n  " + lines.map(_.mkString).mkString("\n  "))
  if lines.length == 1 then
    BigInt(lines.head.mkString, 2).toInt
  else
    val column = lines.transpose.apply(index)
    // Look at indexed column, find most common bit (if equal, keep '1')
    val mostCommonBit = (column.sum.toDouble / column.length).round
    // Filter input list starting with most common bit
    val matches = lines.filterNot(_ (index) == mostCommonBit)
    // Advance index pointer, repeat
    filterForLeastCommon(index + 1, matches)
}


@main def run =
  println("Gamma: " + gamma)
  println("Epsilon: " + epsilon)
  println("Diagnostic code: " + gamma*epsilon)

  val nums = lines.map(_.toCharArray.map(_.toString.toInt)).toArray
  val o2GeneratorRating = filterForMostCommon(0, nums)
  val co2ScrubberRating = filterForLeastCommon(0, nums)

  println("O2 Generator Rating: " + o2GeneratorRating)
  println("C02 Scrubber Rating: " + co2ScrubberRating)
  println("Life Support Rating: " + o2GeneratorRating*co2ScrubberRating)
