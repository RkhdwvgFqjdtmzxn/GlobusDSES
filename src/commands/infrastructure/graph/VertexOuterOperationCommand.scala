package globus.commands.infrastructure.graph

import com.orientechnologies.orient.core.id.ORID
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import globus.commands.infrastructure.graph.internal.ChangeVertexContext
import globus.domain.VertexOuterChangeOperationType.VertexOuterChangeOperationType
import globus.domain.{VertexOperationType, VertexOuterChangeOperation}
import globus.infrastructure.graph.GraphError
import globus.infrastructure.langApi.rop._
import globus.queries.infrastructure.graph.{TermIdByNmeQuery, VertexOperationTypeIdQuery, VertexOuterChangeOperationTypeIdQuery}

class VertexOuterOperationCommand (val operationType: VertexOuterChangeOperationType) extends GraphTypeCommand[VertexOuterChangeOperation] {
  val termIdByNmeQuery = new TermIdByNmeQuery

  val vertexOperationTypeIdQuery = new VertexOperationTypeIdQuery

  val vertexOuterChangeOperationTypeIdQuery = new VertexOuterChangeOperationTypeIdQuery

  def addVertex(operation: VertexOuterChangeOperation): R[ORID, GraphError] = {
    try {
      val operationVertex: OrientVertex = graph addVertex(
        "class: Operation",
        "name", operation.name
      )
      val termId = termIdByNmeQuery get operation.vertexTerm.name match {
        case Succ(data) => data
        case Fail(error) => return fail(error)
      }
      if (termId.isEmpty)
        return fail(new GraphError("There is trying of operation adding to KB, but related term (" + operation.vertexTerm.name + ") isn't exists in KB"))
      val termVertex = graph getVertex termId
      operationVertex addEdge(operationType.toString + "_" + operation.vertexTerm.name, termVertex)
      val vertexOperationTypeId = vertexOperationTypeIdQuery get VertexOperationType.outer
      val vertexOperationTypeVertex = graph getVertex vertexOperationTypeId
      vertexOperationTypeVertex addEdge("HasOperation", operationVertex)
      val vertexOuterChangeOperationTypeId = vertexOuterChangeOperationTypeIdQuery get operationType
      val vertexOuterChangeOperationTypeVertex = graph getVertex vertexOuterChangeOperationTypeId
      vertexOuterChangeOperationTypeVertex addEdge("HasOperation", operationVertex)
      succeed(operationVertex getIdentity)
    } catch {
      case e: Exception => fail(new GraphError("Inner graph error during adding new operation."))
    }
  }

  def changeVertex(context: ChangeVertexContext): R[String, GraphError] = ???
}
