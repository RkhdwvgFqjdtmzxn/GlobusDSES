package globus.domain

import scala.collection.mutable.ArrayBuffer

object LogicalOperationType extends Enumeration {
  type LogicalOperationType = Value
  val vertex, vertexesRelate, vertexesRelateValue, edge, edgeRelateValue = Value

  var operations: Option[ArrayBuffer[Operation]] = _
}
