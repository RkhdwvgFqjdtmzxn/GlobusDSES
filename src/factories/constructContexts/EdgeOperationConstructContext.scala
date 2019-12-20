package factories.constructContexts

import globus.domain.EdgeOperationType.EdgeOperationType
import globus.domain.Term

class EdgeOperationConstructContext (val name: String, val fromTerm: Term, val toTerm: Term, val operationType: EdgeOperationType) {

}
