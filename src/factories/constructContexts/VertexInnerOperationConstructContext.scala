package globus.factories.constructContexts

import globus.domain.NumberOperationType.NumberOperationType
import globus.domain.Term

class VertexInnerOperationConstructContext(override val name: String, val term: Term, val vertexPropName: String)
    extends ChangingOperationConstructContext(name) {
  var numberType: NumberOperationType = _
}
