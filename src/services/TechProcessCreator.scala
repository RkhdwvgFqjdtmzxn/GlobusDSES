package globus.services
import com.orientechnologies.orient.core.id.ORID
import util.control.Breaks._
import globus.app.AppError
import globus.domain.TechProcess
import globus.factories.constructContexts.OperationConstructContext
import globus.infrastructure.langApi.rop._
import globus.queries.infrastructure.graph.GraphContextQueryable
import queries.infrastructure.graph.OperationIdByNmeQuery

import scala.collection.mutable.ArrayBuffer

class TechProcessCreator extends TechProcessCreatable {
  private var errMessage: String = _

  private var operationIdByNmeQuery: GraphContextQueryable[String, ORID] = new OperationIdByNmeQuery

  def create(name: String, termName: String, startOpName: String, opNames: Option[ArrayBuffer[ArrayBuffer[String]]]): R[TechProcess, AppError] = {
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
    } else {
      //to do: create process with relating with ops. through factory and command
      val techProcess = new TechProcess(name)

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

  def create(name: String, startOpConstructContext: OperationConstructContext, opNames: Option[ArrayBuffer[ArrayBuffer[String]]]):
    R[TechProcess, AppError] = ???

  def create(name: String, startOpConstructContext: OperationConstructContext,
             opConstructContexts: Option[ArrayBuffer[ArrayBuffer[OperationConstructContext]]]):
    R[TechProcess, AppError] = ???

  def create(startOpConstructContext: OperationConstructContext, opNames: Option[ArrayBuffer[Map[Int, ArrayBuffer[Map[Int, String]]]]],
             opConstructContexts: Option[ArrayBuffer[Map[Int, ArrayBuffer[Map[Int, OperationConstructContext]]]]]):
    R[TechProcess, AppError] = ???
}
