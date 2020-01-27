package globus.factories.constructContexts

class CycleOperationConstructContext(override val name: String, val operationConstructContext: OperationConstructContext)
    extends OperationConstructContext(name) {

}
