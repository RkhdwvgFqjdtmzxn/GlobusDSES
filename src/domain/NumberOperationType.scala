package globus.domain

import scala.collection.mutable.ArrayBuffer

object NumberOperationType extends Enumeration {
  type NumberOperationType = Value
  var assign, plus, multiply, divide = Value

  var vertexInnerChangeOperations: Option[ArrayBuffer[VertexInnerChangeOperation]] = _

  var vertexInnerChangeLogicalOperations: Option[ArrayBuffer[VertexInnerChangeLogicalOperation]] = _
}
