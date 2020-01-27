package globus.factories.constructContexts

import globus.domain.CompareTextLogicType.CompareTextLogicType
import globus.domain.Term

class EdgeLogicalOperationConstructContext(override val name: String, override val relatedTerm: Term, val edgedTerm: Term,
                                           val compareTextLogicType: CompareTextLogicType)
    extends LogicalOperationConstructContext(name, relatedTerm) {

}
