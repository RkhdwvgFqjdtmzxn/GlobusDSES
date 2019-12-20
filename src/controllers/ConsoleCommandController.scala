package globus.controllers

import com.orientechnologies.orient.core.id.ORID
import globus.app.{AppError, InnerError}
import globus.commands.infrastructure.graph.{GraphCommand, GraphTypeCommand}
import globus.commands.infrastructure.graph.internal.{ChangeVertexContext, DeleteVertexContext}
import globus.domain.Term
import globus.factories.UIManagedFactory
import globus.infrastructure.console.{ConsoleCommandResult, ConsoleError}
import globus.infrastructure.langApi.rop._
import globus.services.{CommandParser, ConsoleResultProvider}
import globus.extensions.StringExtensions.ExtendedString

import scala.collection.mutable.ArrayBuffer
import scala.reflect.{ClassTag, classTag}
import scala.language.postfixOps

class ConsoleCommandController {

  def newTerm(paramsText: String, factory: UIManagedFactory[Term], graphCommand: GraphTypeCommand[Term]): R[ConsoleCommandResult, AppError] = {
    newInstance(paramsText, factory, graphCommand)
  }

  def newInstance[T: ClassTag](paramsText: String, factory: UIManagedFactory[T], graphCommand: GraphTypeCommand[T]): R[ConsoleCommandResult, AppError] = {
    val getParamsList = (paramsText: String) => getParamsAsList(paramsText)
    val createInstance = (paramsList: ArrayBuffer[String]) => factory createFrom paramsList
    val addInstanceToGraph = (t: T) => graphCommand addVertex t
    val getAddToGraphMessage = (recordId: ORID) => getAddVertexToGraphMessage(classTag[T].runtimeClass.getSimpleName, recordId)
    val resultFunc = getParamsList >=> createInstance >=> addInstanceToGraph >=> getAddToGraphMessage
    resultFunc(paramsText)
  }

  private def getParamsAsList(paramsText : String): R[ArrayBuffer[String], AppError] ={
    try {
      succeed(paramsText splitByWhitespace)
    } catch {
      case e: Exception => fail(new InnerError("Inner error! "))
    }
  }

  private def getAddVertexToGraphMessage(vertexType:String, recordId: ORID): R[ConsoleCommandResult, AppError] = {
    if (recordId != null)
      succeed(new ConsoleCommandResult(vertexType + ": " + recordId))
    else
      fail(new ConsoleError("Inner app error in constructing graph message. "))
  }

  def editTerm(paramsList: String, parser: CommandParser, graphCommand: GraphTypeCommand[Term], infoProvider: ConsoleResultProvider): R[String, AppError] = {
    editInstance(paramsList, parser, graphCommand, infoProvider)
  }

  def editInstance[T](paramText:String, parser: CommandParser, graphCommand: GraphTypeCommand[T], infoProvider: ConsoleResultProvider): R[String, AppError] = {
    val parse = (params: String) => parser parseEdit params
    val editInstanceInDb = (changeContext: ChangeVertexContext) => graphCommand changeVertex changeContext
    val getEditInGraphMessage = (id: String) => infoProvider get id
    val resultFunc = parse >=> editInstanceInDb >=> getEditInGraphMessage
    resultFunc(paramText)
  }

  def delTerm(paramText: String, graphCommand: GraphCommand) : R[ConsoleCommandResult, AppError] = {
    val delTrnmtFromDb = (id: String) => graphCommand deleteVertex id
    val getDelFromGraphMessage = (recordId: ORID) => getDelVertexFromGraphMessage("Trnmt", recordId)
    val resultFunc = delTrnmtFromDb >=> getDelFromGraphMessage
    resultFunc(paramText)
  }

  private def getDelVertexFromGraphMessage(vertexType:String, recordId: ORID): R[ConsoleCommandResult, AppError] = {
    if (recordId != null)
      succeed(new ConsoleCommandResult("Deleted " + vertexType + " with id " + recordId))
    else
      fail(new ConsoleError("Inner app error in constructing graph message. "))
  }

  def delTerms(paramsText: String, parser: CommandParser, graphCommand: GraphCommand) : R[ConsoleCommandResult, AppError] = {
    val parse = (params: String) => parser parseDelTrnmts params
    val delTrnmtsFromDb = (deleteContext: DeleteVertexContext) => graphCommand deleteVertexes deleteContext
    val getDelFromGraphMessage = (recordIds: String) => getDelVertexesFromGraphMessages("Trnmts", recordIds)
    val resultFunc = parse >=> delTrnmtsFromDb >=> getDelFromGraphMessage
    resultFunc(paramsText)
  }

  private def getDelVertexesFromGraphMessages(vertexType: String, recordIds: String) : R[ConsoleCommandResult, AppError] = {
    if (recordIds != null)
      succeed(new ConsoleCommandResult("Deleted " + vertexType + " with ids: " + recordIds))
    else
      fail(new ConsoleError("Inner app error in constructing graph message. "))
  }
}