package globus.commands.infrastructure.graph

import com.orientechnologies.orient.core.id.ORID
import globus.domain.TechProcess
import globus.infrastructure.graph.GraphError
import globus.infrastructure.langApi.rop._

import scala.collection.mutable.ArrayBuffer

class TechProcessCreator(techProcess: TechProcess, startOperationContext: Any,
                         operationContexts: Option[ArrayBuffer[Map[Int, ArrayBuffer[Map[Int, ArrayBuffer[Any]]]]]]) extends SubGraphCreator {
  def work(termId: ORID): R[ArrayBuffer[ORID], GraphError] = {
    try {
    val techProcessCommand = new TechProcessCommand(termId)
    val techProcessId = techProcessCommand addVertex techProcess
    val startOperationId =
  }

  private def createStartOperation(): R[ORID, GraphError] = {
    if (startOperationContext.isInstanceOf[String]) {

    }
    //else build Operation and save in KB, use Factory-Command
  }
}
