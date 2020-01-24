package globus.domain

import scala.collection.mutable.ArrayBuffer

object VertexOperationType extends Enumeration {
  type VertexOperationType = Value
  val outer, inner, innerLogical = Value

  var operations: Option[ArrayBuffer[Operation]] = _
}
