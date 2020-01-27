package globus.factories.constructContexts

import globus.domain.Term
import globus.domain.CompareNumLogicType.CompareNumLogicType

class VertexInnerLogicalNumOperationConstructContext(override val name: String, override val relatedTerm: Term, val vertexPropName: String,
                                                  val compareNumType: CompareNumLogicType)
    extends LogicalOperationConstructContext(name, relatedTerm){
}