package globus.domain

import scala.collection.mutable.ArrayBuffer

object EdgeOperationType extends Enumeration {
  type EdgeOperationType = Value
  val outer, inner = Value

  var operations: Option[ArrayBuffer[Operation]] = _
}
