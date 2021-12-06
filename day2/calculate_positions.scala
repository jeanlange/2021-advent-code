import scala.io.Source

def main(): Unit = {
  // val InputFile = "tiny_input.txt"
  val InputFile = "input.txt"

  val lines = Source.fromFile(InputFile).getLines().toList
  val sums = lines.map(_.split(" ")).groupMapReduce(_(0))(_(1).toInt)(_ + _)
  val horizontal = sums.getOrElse("forward", 0)
  val vertical = sums.getOrElse("down", 0) - sums.getOrElse("up", 0)

  println(sums)
  println(horizontal * vertical)
}


main()


def aim(): Unit = {
  //val InputFile = "tiny_input.txt"
  val InputFile = "input.txt"

  val lines = Source.fromFile(InputFile).getLines().toList
  var horizontal = 0
  var depth = 0
  var aim = 0

  lines.foreach {
    case s"forward $d" =>
      horizontal = horizontal + d.toInt
      depth = depth + d.toInt * aim
    case s"up $d" =>
      aim = aim - d.toInt
    case s"down $d" =>
      aim = aim + d.toInt
  }

  println(horizontal * depth)
}

aim()