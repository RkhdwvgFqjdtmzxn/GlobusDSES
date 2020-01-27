package globus.factories.constructContexts

import globus.domain.Term

class EdgeInnerOperationConstructContext(override val name: String, val fromTerm: Term, val toTerm: Term)
    extends ChangingOperationConstructContext(name) {

}
