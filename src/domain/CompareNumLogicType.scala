package globus.domain

import scala.collection.mutable.ArrayBuffer

object CompareNumLogicType extends Enumeration {
  type CompareNumLogicType = Value
  val equal, notEqual, greaterThan, greaterThanOrEqual, lessThan, lessThanOrEqual = Value

  var vertexInnerLogicalOperations: Option[ArrayBuffer[VertexInnerLogicalOperation]] = _
}
