package globus.factories.constructContexts

import globus.domain.CompareTextLogicType.CompareTextLogicType
import globus.domain.Term

class VertexInnerLogicalTextOperationConstructContext(override val name: String, override val relatedTerm: Term, val vertexPropName: String,
                                                      val compareNumType: CompareTextLogicType)
    extends LogicalOperationConstructContext(name, relatedTerm) {
}
