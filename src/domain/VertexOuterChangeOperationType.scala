package globus.domain

import scala.collection.mutable.ArrayBuffer

object VertexOuterChangeOperationType extends Enumeration {
  type VertexOuterChangeOperationType = Value
  val adding, deleting = Value

  var vertexOperations: Option[ArrayBuffer[VertexOuterChangeOperation]] = _

  var vertexLogicalOperations: Option[ArrayBuffer[VertexOuterChangeLogicalOperation]] = _
}
