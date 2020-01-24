package globus.domain

import scala.collection.mutable.ArrayBuffer

object EdgeKindType extends Enumeration {
  type EdgeKindType = Value
  val termToTerm, KindToKind, subKindToSubKind, termToKind, termToSubKind, kindToSubKind = Value

  var operations: Option[ArrayBuffer[EdgeOuterChangeOperation]] = _
}
