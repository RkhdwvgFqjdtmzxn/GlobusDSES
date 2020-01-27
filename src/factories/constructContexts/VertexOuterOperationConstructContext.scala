package globus.factories.constructContexts

import globus.domain.Term
import globus.domain.VertexOuterChangeOperationType.VertexOuterChangeOperationType

class VertexOuterOperationConstructContext(override val name: String, val term: Term, val vertexOuterOperationType: VertexOuterChangeOperationType)
  extends ChangingOperationConstructContext(name){

}
