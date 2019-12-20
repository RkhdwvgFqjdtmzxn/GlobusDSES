package globus.commands.infrastructure.graph

import com.orientechnologies.orient.core.id.ORID
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import globus.commands.infrastructure.graph.internal.ChangeVertexContext
import globus.domain.{EdgeOperationType, EdgeOuterChangeOperation}
import globus.domain.EdgeOuterChangeOperationType.EdgeOuterChangeOperationType
import globus.infrastructure.graph.GraphError
import globus.infrastructure.langApi.rop._
import globus.queries.infrastructure.graph.{EdgeOperationTypeIdQuery, EdgeOuterChangeOperationTypeIdQuery, TermIdByNmeQuery}

class EdgeOuterOperationCommand (val operationType: EdgeOuterChangeOperationType) extends GraphTypeCommand[EdgeOuterChangeOperation]{
  val termIdByNmeQuery = new TermIdByNmeQuery

  val edgeOperationTypeIdQuery = new EdgeOperationTypeIdQuery

  val edgeOuterChangeOperationTypeIdQuery = new EdgeOuterChangeOperationTypeIdQuery

  def addVertex(operation: EdgeOuterChangeOperation): R[ORID, GraphError] = {
    try {
      val operationVertex: OrientVertex = graph addVertex(
        "class: Operation",
        "name", operation.name
      )
      val fromTermVertexId = termIdByNmeQuery get operation.fromVertexTerm.name match {
        case Succ(data) => data
        case Fail(msg) => return fail(new GraphError(""))
      }
      val fromTermVertex = graph getVertex fromTermVertexId
      val toTermVertexId = termIdByNmeQuery get operation.toVertexTerm.name match {
        case Succ(data) => data
        case Fail(msg) => return fail(new GraphError(""))
      }
      val toTermVertex = graph getVertex toTermVertexId
      operationVertex addEdge(operationType + "_" + operation.fromVertexTerm.name, fromTermVertex)
      operationVertex addEdge(operationType + "_" + operation.toVertexTerm.name, toTermVertex)
      val edgeOperationTypeId = edgeOperationTypeIdQuery get EdgeOperationType.outer
      val edgeOperationTypeVertex = graph getVertex edgeOperationTypeId
      edgeOperationTypeVertex addEdge ("HasOperation", operationVertex)
      val edgeOuterChangeOperationTypeId = edgeOuterChangeOperationTypeIdQuery get operationType
      val edgeOuterChangeOperationTypeVertex = graph getVertex edgeOuterChangeOperationTypeId
      edgeOuterChangeOperationTypeVertex addEdge ("HasOperation", operationVertex)
      succeed(operationVertex getIdentity)
    } catch {
      case e: Exception => fail(new GraphError("Inner graph error during adding new operation."))
    }
  }

  def changeVertex(context: ChangeVertexContext): R[String, GraphError] = ???
}
