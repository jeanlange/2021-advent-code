import java.io.{BufferedReader, FileReader}
import scala.util.Using
// Gamma is bit-wise, most-common
// Sample is 10110 = 22
// Epsilon is bit-wise not of gamma
// Sample is 01001 = 9
// Power consumption = 22 x 9 = 198

val inputFile = "sample.txt"

val lines = Using.resource(BufferedReader(FileReader(inputFile))) { reader =>
  Iterator.continually(reader.readLine()).takeWhile(_ != null).toSeq
}

// Assumes good input - only '1' and '0' chars
val bitsByColumn = lines.map(_.toCharArray.map(_.toString.toInt)).transpose
val bits = bitsByColumn.map(c => (c.sum.toDouble / c.length).round).map(_.toString()(0)).mkString
val gamma = BigInt(bits, 2).toInt
val epsilon = ~gamma & BigInt("1"*bits.length, 2).toInt

@main def run =
  println("Gamma: " + gamma)
  println("Epsilon: " + epsilon)
  println("Diagnostic code: " + gamma*epsilon)
