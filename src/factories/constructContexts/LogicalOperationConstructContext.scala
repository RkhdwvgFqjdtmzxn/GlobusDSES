package globus.factories.constructContexts

import globus.domain.Term

abstract class LogicalOperationConstructContext(override val name: String, val relatedTerm: Term)
    extends OperationConstructContext(name) {

}
