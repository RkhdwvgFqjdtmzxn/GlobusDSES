package globus.domain

import scala.collection.mutable.ArrayBuffer

object VertexOperationType extends Enumeration {
  type VertexOperationType = Value
  val outer, outerLogical, inner, innerLogical, logical = Value

  var operations: Option[ArrayBuffer[Operation]] = _
}
