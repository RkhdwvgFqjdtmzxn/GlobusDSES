package globus.domain

class VertexInnerLogicalOperation(override val name: String, override val relatedTerm: Term)
    extends LogicalOperation(name, relatedTerm) {
  var vertexPropValue: String = _
}
