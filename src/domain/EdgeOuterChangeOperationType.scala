package globus.domain

import scala.collection.mutable.ArrayBuffer

object EdgeOuterChangeOperationType extends Enumeration {
  type EdgeOuterChangeOperationType = Value
  val adding, deleting = Value

  var operations: Option[ArrayBuffer[EdgeOuterChangeOperation]] = _

  var interTermsLogicalOperations: Option[ArrayBuffer[EdgeOuterChangeLogicalOperation]] = _
}
