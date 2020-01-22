package globus.domain

class EdgeOuterChangeOperation(override val name: String, override val fromVertexTerm: Term, override val toVertexTerm: Term)
    extends EdgeOperationBase(name, fromVertexTerm, toVertexTerm)  {
}
