package globus.commands.infrastructure.graph

import com.orientechnologies.orient.core.id.ORID
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import globus.commands.infrastructure.graph.internal.ChangeVertexContext
import globus.domain.{VertexInnerChangeOperation, VertexOperationType}
import globus.infrastructure.graph.GraphError
import globus.infrastructure.langApi.rop._
import globus.queries.infrastructure.graph.{TermIdByNmeQuery, VertexOperationTypeIdQuery}

class VertexInnerOperationCommand extends GraphTypeCommand[VertexInnerChangeOperation] {
  val termIdByNmeQuery = new TermIdByNmeQuery

  val vertexOperationTypeIdQuery = new VertexOperationTypeIdQuery

  def addVertex(operation: VertexInnerChangeOperation): R[ORID, GraphError] = {
    try {
      val operationVertex: OrientVertex = graph addVertex(
        "class: Operation",
        "name", operation.name
      )
      val termVertexId = termIdByNmeQuery get operation.vertexTerm.name match {
        case Succ(data) => data
        case Fail(msg) => return fail(new GraphError(""))
      }
      val termVertex = graph getVertex termVertexId
      operationVertex addEdge(operation.vertexTerm.name, termVertex)
      val vertexOperationTypeId = vertexOperationTypeIdQuery get VertexOperationType.inner
      val vertexOperationTypeVertex = graph getVertex vertexOperationTypeId
      vertexOperationTypeVertex addEdge("HasOperation", operationVertex)
      succeed(operationVertex getIdentity)
    } catch {
      case e: Exception => fail(new GraphError("Inner graph error during adding new operation."))
    }
  }

  def changeVertex(context: ChangeVertexContext): R[String, GraphError] = ???
}
