package globus.domain

import scala.collection.mutable.ArrayBuffer

object VertexKindType extends Enumeration {
  type VertexKindType = Value
  val term, termKind, subTermKind = Value

  var vertexOuterChangeOperations: Option[ArrayBuffer[VertexOuterChangeOperation]] = _

  var vertexInnerChangeOperations: Option[ArrayBuffer[VertexInnerChangeOperation]] = _
}
