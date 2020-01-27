package globus.domain

import scala.collection.mutable.ArrayBuffer

object CompareTextLogicType extends Enumeration {
  type CompareTextLogicType = Value
  val equal, notEqual = Value

  var vertexInnerLogicalOperations: Option[ArrayBuffer[VertexInnerLogicalOperation]] = _

  var edgeLogicalOperations: Option[ArrayBuffer[EdgeLogicalOperation]] = _
}
