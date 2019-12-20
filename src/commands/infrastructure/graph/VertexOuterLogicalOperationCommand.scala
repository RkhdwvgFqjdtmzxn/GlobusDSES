package globus.commands.infrastructure.graph

import com.orientechnologies.orient.core.id.ORID
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import globus.commands.infrastructure.graph.internal.ChangeVertexContext
import globus.domain.{VertexOperationType, VertexOuterChangeLogicalOperation}
import globus.domain.VertexOuterChangeOperationType.VertexOuterChangeOperationType
import globus.infrastructure.graph.GraphError
import globus.infrastructure.langApi.rop._
import globus.queries.infrastructure.graph.{TermIdByNmeQuery, VertexOperationTypeIdQuery, VertexOuterChangeOperationTypeIdQuery}

class VertexOuterLogicalOperationCommand(val operationType: VertexOuterChangeOperationType)
  extends GraphTypeCommand[VertexOuterChangeLogicalOperation] {

  val termIdByNmeQuery = new TermIdByNmeQuery

  val vertexOperationTypeIdQuery = new VertexOperationTypeIdQuery

  val vertexOuterChangeOperationTypeIdQuery = new VertexOuterChangeOperationTypeIdQuery

  def addVertex(operation: VertexOuterChangeLogicalOperation): R[ORID, GraphError] = {
    try {
      val operationVertex: OrientVertex = graph addVertex(
        "class: Operation",
        "name", operation.name
      )
      val termId = termIdByNmeQuery get operation.vertexTerm.name match {
        case Succ(data) => data
        case Fail(msg) => return fail(new GraphError(""))
      }
      if (termId.isEmpty)
        return fail(new GraphError("There is trying of operation adding to KB, but related term (" + operation.vertexTerm.name + ") isn't exists in KB"))
      val termVertex = graph getVertex termId
      operationVertex addEdge(operationType + "_" + operation.vertexTerm.name, termVertex)
      val vertexOperationTypeId = vertexOperationTypeIdQuery get VertexOperationType.outer
      val vertexOperationTypeVertex = graph getVertex vertexOperationTypeId
      vertexOperationTypeVertex addEdge("HasOperation", operationVertex)
      val vertexOuterChangeOperationTypeId = vertexOuterChangeOperationTypeIdQuery get operationType
      val vertexOuterChangeOperationTypeVertex = graph getVertex vertexOuterChangeOperationTypeId
      vertexOuterChangeOperationTypeVertex addEdge("HasOperation", operationVertex)
      operation.relatedTerms
    } catch {

    }
  }

  def changeVertex(context: ChangeVertexContext): R[String, GraphError] = ???
}
