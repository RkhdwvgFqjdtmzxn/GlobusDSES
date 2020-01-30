package globus.commands.infrastructure.graph

import com.orientechnologies.orient.core.id.ORID
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import globus.commands.infrastructure.graph.internal.ChangeVertexContext
import globus.domain.{EdgeInnerChangeOperation, EdgeOperationType}
import globus.infrastructure.graph.GraphError
import globus.infrastructure.langApi.rop._
import globus.queries.infrastructure.graph.{EdgeOperationTypeIdQuery, TermIdByNmeQuery}

class EdgeInnerOperationCommand extends GraphTypeCommand[EdgeInnerChangeOperation] {
  val termIdByNmeQuery = new TermIdByNmeQuery

  val edgeOperationTypeIdQuery = new EdgeOperationTypeIdQuery

  def addVertex(operation: EdgeInnerChangeOperation): R[ORID, GraphError] = {
    try {
      val operationVertex: OrientVertex = graph addVertex(
        "class: Operation",
        "name", operation.name
      )
      val fromTermVertexId = termIdByNmeQuery get operation.fromVertexTerm.name match {
        case Succ(data) => data
        case Fail(_) => return fail(new GraphError("Related term for operation not found in KB"))
      }
      if (fromTermVertexId == null)
        return fail(new GraphError("There is trying of operation adding to KB, but related term (" + operation.fromVertexTerm.name + ") isn't exists in KB"))
      val toTermVertexId = termIdByNmeQuery get operation.toVertexTerm.name match {
        case Succ(data) => data
        case Fail(_) => return fail(new GraphError("Related term for operation not found in KB"))
      }
      if (toTermVertexId == null)
        return fail(new GraphError("There is trying of operation adding to KB, but related term (" + operation.toVertexTerm.name + ") isn't exists in KB"))
      val fromTermVertex = graph getVertex fromTermVertexId
      val toTermVertex = graph getVertex toTermVertexId
      operationVertex addEdge("EdgeRelateOut", fromTermVertex)
      operationVertex addEdge("EdgeRelateIn", toTermVertex)
      val edgeOperationTypeId = edgeOperationTypeIdQuery get EdgeOperationType.inner
      val edgeOperationTypeVertex = graph getVertex edgeOperationTypeId
      edgeOperationTypeVertex addEdge("HasEdgeOperation", operationVertex)
      succeed(operationVertex getIdentity)
    } catch {
      case e: Exception => fail(new GraphError("Inner graph error during adding new edge inner operation."))
    }
  }

  def changeVertex(context: ChangeVertexContext): R[String, GraphError] = ???
}
