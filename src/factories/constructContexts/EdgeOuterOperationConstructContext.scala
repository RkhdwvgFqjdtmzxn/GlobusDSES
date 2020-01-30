package globus.factories.constructContexts

import globus.domain.EdgeOuterChangeOperationType.EdgeOuterChangeOperationType
import globus.domain.Term

class EdgeOuterOperationConstructContext (override val name: String, val fromTerm: Term, val toTerm: Term,
                                          val edgeOuterOperationType: EdgeOuterChangeOperationType)
   extends ChangingOperationConstructContext(name){

}
