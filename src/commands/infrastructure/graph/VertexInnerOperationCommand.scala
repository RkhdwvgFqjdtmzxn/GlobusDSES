package globus.commands.infrastructure.graph

import com.orientechnologies.orient.core.id.ORID
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import globus.commands.infrastructure.graph.internal.ChangeVertexContext
import globus.domain.NumberOperationType.NumberOperationType
import globus.domain.{VertexInnerChangeOperation, VertexOperationType}
import globus.infrastructure.graph.GraphError
import globus.infrastructure.langApi.rop._
import globus.queries.infrastructure.graph.{NumberOperationTypeIdQuery, TermIdByNmeQuery, VertexOperationTypeIdQuery}

class VertexInnerOperationCommand extends GraphTypeCommand[VertexInnerChangeOperation] {
  private var numberType: NumberOperationType = _

  def this(numberType: NumberOperationType){
    this()
    this.numberType = numberType
  }

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
        case Fail(msg) => return fail(new GraphError("Related term for operation not found in KB"))
      }
      val termVertex = graph getVertex termVertexId
      operationVertex addEdge("RelateWith_" + operation.vertexPropName, termVertex)
      if (numberType != null) {
        val numberOperationTypeIdQuery = new NumberOperationTypeIdQuery
        val numberOperationTypeId = numberOperationTypeIdQuery get numberType
        val numberOperationTypeVertex = graph getVertex numberOperationTypeId
        numberOperationTypeVertex addEdge("UseInOperation", operationVertex)
      }
      val vertexOperationTypeId = vertexOperationTypeIdQuery get VertexOperationType.inner
      val vertexOperationTypeVertex = graph getVertex vertexOperationTypeId
      vertexOperationTypeVertex addEdge ("HasOperation", operationVertex)
      succeed(operationVertex getIdentity)
    } catch {
      case e: Exception => fail(new GraphError("Inner graph error during adding new operation."))
    }
  }

  def changeVertex(context: ChangeVertexContext): R[String, GraphError] = ???
}
