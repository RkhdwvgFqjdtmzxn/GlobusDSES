package globus.domain

class VertexesRelateValueLogicalOperation(override val name: String, override val relatedTerm: Term, val edgedTerm: Term,
                                          val edgedVertexPropName: String)
    extends LogicalOperation(name, relatedTerm) {
  var edgedVertexPropValue: String = _
}
