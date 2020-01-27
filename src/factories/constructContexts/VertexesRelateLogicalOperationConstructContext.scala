package globus.factories.constructContexts

import globus.domain.Term

class VertexesRelateLogicalOperationConstructContext(override val name: String, override val relatedTerm: Term, val edgedTerm: Term)
    extends LogicalOperationConstructContext(name, relatedTerm) {

}
