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
def filterForBits(lines: Array[Array[Int]], mostCommon: Boolean = true, index: Int = 0): Int = {
  if lines.length == 1 then
    BigInt(lines.head.mkString, 2).toInt
  else
    val column = lines.transpose.apply(index)
    // Look at indexed column, find most common bit (if equal, keep '1')
    val mostCommonBit = (column.sum.toDouble / column.length).round
    val matches =
      if (mostCommon) lines.filter((_ (index) == mostCommonBit))
      else lines.filter(_ (index) != mostCommonBit)
    filterForBits(matches, mostCommon, index + 1)
}

@main def run =
  println("Gamma: " + gamma)
  println("Epsilon: " + epsilon)
  println("Diagnostic code: " + gamma*epsilon)

  val nums = lines.map(_.toCharArray.map(_.toString.toInt)).toArray
  val o2GeneratorRating = filterForBits(nums)
  val co2ScrubberRating = filterForBits(nums, mostCommon = false)

  println("O2 Generator Rating: " + o2GeneratorRating)
  println("C02 Scrubber Rating: " + co2ScrubberRating)
  println("Life Support Rating: " + o2GeneratorRating*co2ScrubberRating)
