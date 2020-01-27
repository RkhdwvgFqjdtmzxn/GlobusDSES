package globus.services
import com.orientechnologies.orient.core.id.ORID
import globus.app.AppError
import globus.commands.infrastructure.graph.TechProcessCommand
import globus.domain.{TechProcess, Term}
import globus.factories.constructContexts.OperationConstructContext
import globus.infrastructure.graph.GraphError
import globus.infrastructure.langApi.rop._
import globus.queries.infrastructure.graph.{GraphContextQueryable, TermIdByNmeQuery}
import queries.infrastructure.graph.OperationIdByNmeQuery

import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks._

class TechProcessCreator extends TechProcessCreatable {
  private var errMessage: String = _

  private val operationIdByNmeQuery: GraphContextQueryable[String, ORID] = new OperationIdByNmeQuery

  private val termIdByNmeQuery: GraphContextQueryable[String, ORID] = new TermIdByNmeQuery

  def create(name: String, term: Term, applyingTerm: Term, startOpName: String, opNames: Option[ArrayBuffer[ArrayBuffer[String]]],
             opValueNums: Option[Map[(Int, Int), (Int, Int)]] = None)
      : R[TechProcess, AppError] = {
    val startOpId: ORID = getStartOpId(startOpName)
    val opIds: Option[ArrayBuffer[ArrayBuffer[ORID]]] = getOpIds(opNames)
    var hasNullOpIds = false
    breakable {
      for (itemIds <- opIds) {
        if (itemIds.contains(null)) {
          hasNullOpIds = true
          break
        }
      }
    }
    if (startOpId == null || opIds.isEmpty || hasNullOpIds) {
      errMessage += "\r\n There are cases of null or empty conditions, not existing in KB operations or app errors"
      fail(new ServiceError("In creating techProcess " + name + ": " + errMessage))
    } else {
      val termId = termIdByNmeQuery get term.name match {
        case Succ(data) => data
        case Fail(msg) => return fail(msg)
      }
      val applyingTermId = termIdByNmeQuery get applyingTerm.name match {
        case Succ(data) => data
        case Fail(msg) => return fail(msg)
      }
      val techProcess = new TechProcess(name)
      val command = new TechProcessCommand(termId, applyingTermId, startOpId, opIds.get, opValueNums)
      val techProcessId = command.addVertex(techProcess) match {
        case Succ(data) => data
        case Fail(msg) => return fail(msg)
      }
      techProcess.id = techProcessId
      succeed(techProcess)
    }
  }

  private def getStartOpId(startOpName: String): ORID = {
    getOpId(startOpName)
  }

  private def getOpIds(opNames: Option[ArrayBuffer[ArrayBuffer[String]]]): Option[ArrayBuffer[ArrayBuffer[ORID]]] = {
    var localErrMsg = ""
    var ids = new ArrayBuffer[ArrayBuffer[ORID]]
    val curOpNames = opNames.getOrElse(None)
    if (curOpNames == None) {
      errMessage += "\r\n Operations (without start) for creating TechProcess is unknown"
      return None
    }
    for (itemOpNames <- curOpNames){
      var itemIds = new ArrayBuffer[ORID]
      if (itemIds == null) {
        localErrMsg += "\r\n Operations in stage " + (ids.length + 1) + "isn't exists in conditions"
      } else {
        for (name <- itemOpNames) {
          val id = getOpId(name)
          itemIds += id
        }
        ids += itemIds
      }
    }
    if (localErrMsg.nonEmpty)
      errMessage += localErrMsg
    Some(ids)
  }

  private def getOpId(opName: String): ORID = {
    var localErrMsg = ""
    val id = operationIdByNmeQuery get opName match {
      case Succ(data) => data
      case Fail(msg) => {localErrMsg += "\r\n" + msg; null}
    }
    if (id == null && localErrMsg.nonEmpty)
      localErrMsg += "\r\n operation " + opName + " isn't exists in KB"
    if (localErrMsg.nonEmpty)
      errMessage += localErrMsg
    id
  }

  def create(name: String, startOpName: String, opConstructContexts: Option[ArrayBuffer[ArrayBuffer[OperationConstructContext]]]):
    R[TechProcess, AppError] = ???

  def create(name: String, startOpName: String, opNames: Option[ArrayBuffer[Map[Int, ArrayBuffer[Map[Int, String]]]]],
             opConstructContexts: Option[ArrayBuffer[Map[Int, ArrayBuffer[Map[Int, OperationConstructContext]]]]]):
    R[TechProcess, AppError] = ???

  def create(name: String,
             startOpConstructContext: OperationConstructContext, opNames: Option[ArrayBuffer[ArrayBuffer[String]]]):
    R[TechProcess, AppError] = ???

  def create(name: String, term: Term, applyingTerm: Term, startOpConstructContext: OperationConstructContext,
             opConstructContexts: Option[ArrayBuffer[ArrayBuffer[OperationConstructContext]]],
               opValueNums: Option[Map[(Int, Int), (Int, Int)]] = None):
    R[TechProcess, AppError] = {

  }

  private def createStartOperation(startOpConstructContext: OperationConstructContext): R[ORID, GraphError] = {

  }

  def create(startOpConstructContext: OperationConstructContext, opNames: Option[ArrayBuffer[Map[Int, ArrayBuffer[Map[Int, String]]]]],
             opConstructContexts: Option[ArrayBuffer[Map[Int, ArrayBuffer[Map[Int, OperationConstructContext]]]]]):
    R[TechProcess, AppError] = ???
}
