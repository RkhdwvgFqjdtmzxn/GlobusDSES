package globus.services

import globus.app.{AppError, AppFileSystem}
import globus.commands.infrastructure.graph.{GraphCommand, TermCommand}
import globus.controllers.{ConsoleCommandController, ConsoleQueryController}
import globus.domain.Term
import globus.factories.TermFactory
import globus.infrastructure.console.ConsoleCommandResult
import globus.infrastructure.langApi.rop._
import globus.services.domainProviders.TermInfoProvider

import scala.collection.mutable.ArrayBuffer
import scala.language.postfixOps

class ConsoleAppShell extends ConsoleShell {
  private var curNum = 0

  private var terms = new ArrayBuffer[Term]

  private val helpShell = new ConsoleHelpShell

  private val consoleCommand = new ConsoleCommandController

  private val consoleQuery = new ConsoleQueryController

  private val parser = new CommandParser

  private val graphCommand = new GraphCommand

  private val termCommand = new TermCommand

  def isExists: Boolean = console.isDefined

  def execCommand(command: String): Unit = {
    var isTest = false
    if (command.length >= 6){
      if (command.substring(0, 5) == "test:")
        isTest = true
    }
    var curCommand = if (isTest) command.substring(5) else command
    curCommand match {
      case "?" => help
      case "man" => help
      case "help" => help
      //      case "getAllTerms" => getAllTerms
      //      case "tl" => getAllTerms()
      //      case "tc" => getDetailedTermInfo(0)
      //      case "th" => getDetailedTermInfo(curNum - 1)
      //      case "tj" => getDetailedTermInfo(curNum + 1)
      //case "exit" => Platform.exit
      case _ => execParamCommand(curCommand)
    }
    if (!isTest)
      execCommand(console.get.readLine)
  }

  private def execParamCommand(command: String): Unit = {
    val paramsList = command.substring(command.indexOf(" ") + 1)
    if (command.contains("help") || command.contains("man") || command.contains("?")){
      helpShell help paramsList
    }
    else if (command.contains("setAppFilesDir")){
      setAppFilesDir(paramsList)
    }
    else if (command.contains("newTerm")) {
      val preResult = consoleCommand newTerm(paramsList, new TermFactory, termCommand)
      writeResult(preResult)
    }
    else if (command.contains("ti")){
      writeResult(paramsList, new TermInfoProvider)
    }
    else if (command.contains("editTerm")) {
      val preResult = consoleCommand editTerm(paramsList, parser, termCommand, new TermInfoProvider)
      writeResult(preResult)
    }
    else if (command.contains("delTerm") && !command.contains("delTerms")){
      val preResult = consoleCommand delTerm(paramsList, graphCommand)
      writeResult(preResult)
    }
    else if (command.contains("delTerms")){
      val preResult = consoleCommand delTerms(paramsList, parser, graphCommand)
      writeResult(preResult)
    }
    else if (command.contains("getAllTerms") || command.contains("tl")){

    }
    else if (command.contains("tc")){

    }
    else if (command.contains("th")){

    }
    else if (command.contains("tj")){

    }
    else
      writeLine("Command not found \r\n")
  }

  private def setAppFilesDir(paramsList: String): Unit = {
    AppFileSystem.appFilesDir = paramsList
    writeLine("new app files directory: " + AppFileSystem.appFilesDir)
  }

  private def writeResult(paramsList: String, provider: ConsoleResultProvider): Unit = {
    val preResult = provider get paramsList
    val termInfoMess = preResult match {
      case Succ(s) => s
      case Fail(f) => f.message
    }
    writeLine(termInfoMess)
  }

  private def writeResult(text: => R[String, AppError]): Unit = {
    text match {
      case result: R[String, AppError] =>
        val message = result match {
          case Succ(s) => s
          case Fail(f) => f.message
        }
        writeLine(message)
      case _ =>
    }
  }

  private def writeResult(commandResult: R[ConsoleCommandResult, AppError]): Unit = {
    commandResult match {
      case result: R[ConsoleCommandResult, AppError] =>
        val message = result match {
          case Succ(s) => s.message
          case Fail(f) => f.message
        }
        writeLine(message)
      case _ =>
    }
  }

  def help(): Unit = {
    helpShell help
  }

  //  def getAllTerms() = {
  //    val result: R[ArrayBuffer[Term], GraphError] = getTerms
  //    val message = result match {
  //      case Succ(s) => {
  //        buildTermsText(s)
  //      }
  //      case Fail(f) => f.message
  //    }
  //    writeLine(message)
  //  }
  //
  //  private def buildTermsText(terms: ArrayBuffer[Term]): String = {
  //    val builder = StringBuilder.newBuilder
  //    for (i <- terms.indices) {
  //      builder append terms(i).toString
  //      builder append "\r\n"
  //    }
  //    builder.toString
  //  }
  //
  //  def getDetailedTermInfo(num: Int) = {
  //    curNum = num
  //    if (terms.isEmpty){
  //      terms = getTerms match {
  //        case Succ(s) => s
  //        case Fail(f) => null
  //      }
  //    }
  //    if(terms != null && terms.nonEmpty){
  //      if (curNum < 0)
  //        curNum = terms.size - 1
  //      if (curNum >= terms.size)
  //        curNum = 0
  //      if (curNum >= 0){
  //        val term = terms(curNum)
  //        val termId = term.id.toString()
  //        execParamCommand("getTermInfo " + termId)
  //        execParamCommand("getTermRunds " + termId)
  //      }
  //    }
  //    else{
  //      writeLine("There are not terms")
  //    }
  //  }
  //
  //  private def getTerms: R[ArrayBuffer[Term], GraphError] = {
  //    val termsQuery = new TermsQuery
  //    termsQuery get
  //  }
}
