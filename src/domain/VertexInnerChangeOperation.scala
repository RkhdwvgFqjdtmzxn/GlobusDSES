package globus.domain

class VertexInnerChangeOperation(override val name: String, override val vertexTerm: Term, val vertexPropName: String)
    extends VertexInnerChangeOperationBase(name, vertexTerm) {
  var vertexPropValue: String = _
}
