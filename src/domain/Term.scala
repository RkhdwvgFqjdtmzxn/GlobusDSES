package globus.domain

import globus.domain.TermType.TermType

import scala.collection.mutable.ArrayBuffer

class Term (val name: String, val termType: TermType, val description: String) {
  var termKinds: Option[ArrayBuffer[TermKind]] = _

  override def toString: String = {
    val builder = new StringBuilder
    builder.append(name)
    builder.append(" - ")
    builder.append(description)
    builder.toString()
  }
}
