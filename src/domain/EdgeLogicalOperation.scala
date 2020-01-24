package globus.domain

class EdgeLogicalOperation(override val name: String, override val relatedTerm: Term, val edgedTerm: Term)
    extends LogicalOperation(name, relatedTerm) {
  var edgeNameValue: String = _
}
