package globus.domain

class VertexInnerLogicalOperation(override val name: String, override val relatedTerm: Term, val vertexPropName: String)
    extends LogicalOperation(name, relatedTerm) {
}
