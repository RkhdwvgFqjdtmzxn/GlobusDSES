package commands.infrastructure.graph.internal.workers

import com.orientechnologies.orient.core.id.ORID
import com.tinkerpop.blueprints.impls.orient.{OrientGraph, OrientVertex}
import globus.domain.VertexOuterChangeOperationType.VertexOuterChangeOperationType
import globus.domain.{VertexOperationType, VertexOuterChangeOperation}
import globus.infrastructure.graph.GraphError
import globus.infrastructure.langApi.rop.{Fail, R, Succ, fail, succeed}
import globus.queries.infrastructure.graph.{TermIdByNmeQuery, VertexOperationTypeIdQuery, VertexOuterChangeOperationTypeIdQuery}

class VertexOuterOperationWorker(val graph: OrientGraph, val operationType: VertexOuterChangeOperationType) {
  val termIdByNmeQuery = new TermIdByNmeQuery

  val vertexOperationTypeIdQuery = new VertexOperationTypeIdQuery

  val vertexOuterChangeOperationTypeIdQuery = new VertexOuterChangeOperationTypeIdQuery

  def addVertex(operation: VertexOuterChangeOperation): R[OrientVertex, GraphError] = {
    try {
      val operationVertex: OrientVertex = graph addVertex(
        "class: Operation",
        "name", operation.name
      )
      val termId = termIdByNmeQuery get operation.vertexTerm.name match {
        case Succ(data) => data
        case Fail(error) => return fail(error)
      }
      if (termId == null)
        return fail(new GraphError("There is trying of operation adding to KB, but related term (" + operation.vertexTerm.name + ") isn't exists in KB"))
      val termVertex = graph getVertex termId
      operationVertex addEdge(operationType.toString + "_" + operation.vertexTerm.name, termVertex)
      val vertexOperationTypeId = vertexOperationTypeIdQuery get VertexOperationType.outer
      val vertexOperationTypeVertex = graph getVertex vertexOperationTypeId
      vertexOperationTypeVertex addEdge("HasOperation", operationVertex)
      val vertexOuterChangeOperationTypeId = vertexOuterChangeOperationTypeIdQuery get operationType
      val vertexOuterChangeOperationTypeVertex = graph getVertex vertexOuterChangeOperationTypeId
      vertexOuterChangeOperationTypeVertex addEdge("HasOperation", operationVertex)
      succeed(operationVertex)
    } catch {
      case e: Exception => fail(new GraphError("Inner graph error during adding new operation."))
    }
  }
}
