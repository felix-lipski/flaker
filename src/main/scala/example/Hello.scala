package example

import scala.io.Source
import zio._
import zio.process._

object Main extends scala.App {
  val program =
    for {
      roots <- ZIO.effectTotal(Source.fromFile("/home/felix/.config/flaker/roots").getLines().toIterable)

      lockPaths <- ZIO
        .foreach(roots)(Command("find", _, "-name", "flake.lock", "-type", "f").string)
        .map(_.flatMap(_.split("\n")))

      _ <- console.putStrLn(lockPaths.toString())
    } yield ()

  Runtime.default.unsafeRunSync(program)
}
