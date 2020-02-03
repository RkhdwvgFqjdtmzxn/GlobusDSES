package globus.commands.infrastructure.graph

import com.orientechnologies.orient.core.id.ORID
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import globus.commands.infrastructure.graph.internal.ChangeVertexContext
import globus.domain.{VertexOperationType, VertexOuterChangeOperation}
import globus.domain.VertexOuterChangeOperationType.VertexOuterChangeOperationType
import globus.infrastructure.graph.GraphError
import globus.infrastructure.langApi.rop._
import globus.queries.infrastructure.graph.{TermIdByNmeQuery, VertexOperationTypeIdQuery, VertexOuterChangeOperationTypeIdQuery}
import scala.language.postfixOps

class VertexOuterOperationCommand (val operationType: VertexOuterChangeOperationType) extends GraphTypeCommand[VertexOuterChangeOperation] {
  val termIdByNmeQuery = new TermIdByNmeQuery

  val vertexOperationTypeIdQuery = new VertexOperationTypeIdQuery

  val vertexOuterChangeOperationTypeIdQuery = new VertexOuterChangeOperationTypeIdQuery

  def addVertex(operation: VertexOuterChangeOperation): R[ORID, GraphError] = {
    graph begin;
    try {
      val operationVertex: OrientVertex = graph addVertex(
        "class: Operation",
        "name", operation.name
      )
      val termVertexId = termIdByNmeQuery get operation.vertexTerm.name match {
        case Succ(data) => data
        case Fail(_) => return fail(new GraphError("Related term for operation not found in KB"))
      }
      if (termVertexId == null)
        return fail(new GraphError("There is trying of operation adding to KB, but related term (" + operation.vertexTerm.name + ") isn't exists in KB"))
      val termVertex = graph getVertex termVertexId
      operationVertex addEdge(operationType.toString, termVertex)
      val vertexOperationTypeId = vertexOperationTypeIdQuery get VertexOperationType.outer
      val vertexOperationTypeVertex = graph getVertex vertexOperationTypeId
      vertexOperationTypeVertex addEdge("HasVertexOperation", operationVertex)
      val vertexOuterChangeOperationTypeId = vertexOuterChangeOperationTypeIdQuery get operationType
      val vertexOuterChangeOperationTypeVertex = graph getVertex vertexOuterChangeOperationTypeId
      vertexOuterChangeOperationTypeVertex addEdge("HasOuterOperation", operationVertex)
      graph commit;
      succeed(operationVertex getIdentity)
    } catch {
      case e: Exception => {
        graph rollback;
        fail(new GraphError("Inner graph error during adding new vertex outer operation."))
      }
    }
  }

  def changeVertex(context: ChangeVertexContext): R[String, GraphError] = ???
}
