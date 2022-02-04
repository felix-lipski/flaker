package example

import zio._
import zio.process._
import java.io._

object Main extends scala.App {
  val program =
    for {
       _ <- console.putStrLn("home dir?")
       homeDir <- ZIO.succeed("/home/felix/")
       _ <- console.putStrLn(s"Your home is at: $homeDir")
       _ <- console.putStrLn("─" * 11)

       flakeDirs <- Command("find", "/home/felix/", "-name", "flake.lock", "-type", "f").string
       _ <- console.putStrLn(flakeDirs)
    } yield ()

  Runtime.default.unsafeRunSync(program)
}
