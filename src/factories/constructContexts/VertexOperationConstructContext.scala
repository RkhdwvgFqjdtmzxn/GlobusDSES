package globus.factories.constructContexts

import globus.domain.Term
import globus.domain.VertexOperationType.VertexOperationType

class VertexOperationConstructContext(override val name: String, val term: Term, val operationType: VertexOperationType)
  extends OperationConstructContext(name){

}
