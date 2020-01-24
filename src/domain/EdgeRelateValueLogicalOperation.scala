package globus.domain

class EdgeRelateValueLogicalOperation(override val name: String, override val relatedTerm: Term, val edgedTerm: Term,
                                      val edgedVertexPropName: String)
    extends LogicalOperation(name, relatedTerm) {
  var edgeNameValue: String = _

  var edgedVertexPropValue: String = _
}
