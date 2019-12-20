package globus.services

import java.io.Console

abstract class ConsoleShell {

  protected val console: Option[Console] = Option(System.console())

  def write(symbol: String): Unit = {
    if (console.isDefined)
      console.get.writer().write(symbol)
  }

  def writeLine(line: String): Unit = {
    if (console.isDefined)
      console.get.printf(line + "\r\n")
  }

  def readLine(): String = {
    if (console.isDefined)
      console.get.readLine
    else ""
  }
}
