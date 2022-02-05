package example

import scala.io.Source
import zio._
import zio.process._
import java.io._

object Main extends scala.App {
  val program =
    for {
      roots <- ZIO.succeed(
        Source.fromFile("/home/felix/.config/flaker/roots").getLines()
      )
      _ <- console.putStrLn("home dir?")
      homeDir <- ZIO.succeed("/home/felix/")
      _ <- console.putStrLn(s"Your home is at: $homeDir")
      _ <- console.putStrLn("─" * 11)

      flakeDirs <- Command("find", "/home/felix/", "-name", "flake.lock", "-type", "f").string
      _ <- console.putStrLn(flakeDirs)
      _ <- console.putStrLn(roots.foldLeft("")((a,b) => a ++ "\n" ++ b))
    } yield ()

  Runtime.default.unsafeRunSync(program)
}
