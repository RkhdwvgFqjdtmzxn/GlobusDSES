package globus.domain

import scala.collection.mutable.ArrayBuffer

object LogicalRelateOperationType extends Enumeration {
  type LogicalRelateOperationType = Value
  val vertexesRelate, vertexesRelateValue, edge, edgeRelateValue = Value

  var vertexesRelateOperations: Option[ArrayBuffer[VertexesRelateLogicalOperation]] = _

  var vertexesRelateValueOperations: Option[ArrayBuffer[VertexesRelateValueLogicalOperation]] = _

  var edgeOperations: Option[ArrayBuffer[EdgeLogicalOperation]] = _

  var edgeRelateValueOperations: Option[ArrayBuffer[EdgeRelateValueLogicalOperation]] = _
}
