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
  val fwd = "forward (\\d)".r
  val up = "up (\\d)".r
  val down = "down (\\d)".r

  //val InputFile = "tiny_input.txt"
  val InputFile = "input.txt"

  val lines = Source.fromFile(InputFile).getLines().toList
  var horizontal = 0
  var depth = 0
  var aim = 0

  lines.foreach {
    case fwd(d) =>
      horizontal = horizontal + d.toInt
      depth = depth + d.toInt * aim
    case up(d) =>
      aim = aim - d.toInt
    case down(d) =>
      aim = aim + d.toInt
  }

  println(horizontal * depth)
}

aim()