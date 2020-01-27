package globus.factories.constructContexts

import globus.domain.EdgeOperationType.EdgeOperationType
import globus.domain.Term

class EdgeOuterOperationConstructContext (override val name: String, val fromTerm: Term, val toTerm: Term,
                                          val edgeOuterOperationType: EdgeOperationType)
   extends ChangingOperationConstructContext(name){

}
