package globus.factories.constructContexts

import globus.domain.EdgeOperationType.EdgeOperationType
import globus.domain.Term

class EdgeOperationConstructContext (override val name: String, val fromTerm: Term, val toTerm: Term, val operationType: EdgeOperationType)
   extends OperationConstructContext(name){

}
