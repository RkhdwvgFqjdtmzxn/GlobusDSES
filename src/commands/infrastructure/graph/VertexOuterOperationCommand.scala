package globus.commands.infrastructure.graph

import com.orientechnologies.orient.core.id.ORID
import commands.infrastructure.graph.internal.workers.VertexOuterOperationWorker
import globus.commands.infrastructure.graph.internal.ChangeVertexContext
import globus.domain.VertexOuterChangeOperation
import globus.domain.VertexOuterChangeOperationType.VertexOuterChangeOperationType
import globus.infrastructure.graph.GraphError
import globus.infrastructure.langApi.rop._

class VertexOuterOperationCommand (val operationType: VertexOuterChangeOperationType) extends GraphTypeCommand[VertexOuterChangeOperation] {
  val worker = new VertexOuterOperationWorker(graph, operationType)

  def addVertex(operation: VertexOuterChangeOperation): R[ORID, GraphError] = {
    try {
      val operationVertex = worker addVertex operation match {
        case Succ(data) => data
        case Fail(msg) => return fail(msg)
      }
      succeed(operationVertex.getIdentity)
    } catch {
      case e: Exception => fail(new GraphError("Inner graph error during adding new operation."))
    }
  }

  def changeVertex(context: ChangeVertexContext): R[String, GraphError] = ???
}
