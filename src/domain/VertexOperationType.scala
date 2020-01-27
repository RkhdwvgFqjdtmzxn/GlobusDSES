package globus.domain

import scala.collection.mutable.ArrayBuffer

object VertexOperationType extends Enumeration {
  type VertexOperationType = Value
  val outer, inner = Value

  var operations: Option[ArrayBuffer[Operation]] = _
}
