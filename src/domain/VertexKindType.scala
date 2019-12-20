package globus.domain

import scala.collection.mutable.ArrayBuffer

object VertexKindType extends Enumeration {
  type VertexKindType = Value
  val term, termKind, subTermKind = Value

  var vertexOuterChangeOperations: Option[ArrayBuffer[VertexOuterChangeOperation]] = _

  var vertexInnerChangeOperations: Option[ArrayBuffer[VertexInnerChangeOperation]] = _

  var vertexOuterChangeLogicalOperations: Option[ArrayBuffer[VertexOuterChangeLogicalOperation]] = _

  var vertexInnerChangeLogicalOperations: Option[ArrayBuffer[VertexInnerChangeLogicalOperation]] = _
}
