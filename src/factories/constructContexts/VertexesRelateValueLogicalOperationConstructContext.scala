package globus.factories.constructContexts

import globus.domain.Term

class VertexesRelateValueLogicalOperationConstructContext(override val name: String, override val relatedTerm: Term, val edgedTerm: Term,
val edgedVertexPropName: String)
    extends LogicalOperationConstructContext(name, relatedTerm) {

}
