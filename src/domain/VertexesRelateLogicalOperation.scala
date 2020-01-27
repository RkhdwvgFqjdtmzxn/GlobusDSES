package globus.domain

class VertexesRelateLogicalOperation(override val name: String, override val relatedTerm: Term, val edgedTerm: Term)
    extends LogicalOperation(name, relatedTerm) {
}
