package globus.domain

import scala.collection.mutable.ArrayBuffer

object TermType extends Enumeration {
  type TermType = Value
  val obj, event, characteristic = Value

  var terms: Option[ArrayBuffer[Term]] = _
}
