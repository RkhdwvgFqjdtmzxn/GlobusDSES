package globus.commands.infrastructure.graph

import com.orientechnologies.orient.core.id.ORID
import commands.infrastructure.graph.internal.workers.VertexOuterOperationWorker
import globus.commands.infrastructure.graph.internal.ChangeVertexContext
import globus.domain.VertexOuterChangeLogicalOperation
import globus.domain.VertexOuterChangeOperationType.VertexOuterChangeOperationType
import globus.infrastructure.graph.GraphError
import globus.infrastructure.langApi.rop._
import globus.queries.infrastructure.graph.{TermIdByNmeQuery, VertexOperationTypeIdQuery, VertexOuterChangeOperationTypeIdQuery}

class VertexOuterLogicalOperationCommand(val operationType: VertexOuterChangeOperationType)
  extends GraphTypeCommand[VertexOuterChangeLogicalOperation] {
  val worker = new VertexOuterOperationWorker(graph, operationType)

  val termIdByNmeQuery = new TermIdByNmeQuery

  val vertexOperationTypeIdQuery = new VertexOperationTypeIdQuery

  val vertexOuterChangeOperationTypeIdQuery = new VertexOuterChangeOperationTypeIdQuery

  def addVertex(operation: VertexOuterChangeLogicalOperation): R[ORID, GraphError] = {
    try {
      val operationVertex = worker.addVertex(operation) match {
        case Succ(data) => data
        case Fail(msg) => return fail(msg)
      }
      for (term <- operation.relatedTerms.get){
        val termId = termIdByNmeQuery get term.name
        val termVertex = graph getVertex termId
        operationVertex addEdge("Relate with", termVertex)
      }
      succeed(operationVertex getIdentity)
    } catch {
      case e: Exception => fail(new GraphError("Inner graph error during adding new operation."))
    }
  }

  def changeVertex(context: ChangeVertexContext): R[String, GraphError] = ???
}
