package globus.factories.constructContexts

import scala.collection.mutable.ArrayBuffer

class LogicManagedOperationConstructContext(override val name: String,
                                            val logicalOperationConstructContexts: ArrayBuffer[LogicalOperationConstructContext],
                                            val execOperationConstructContext: ChangingOperationConstructContext)
    extends OperationConstructContext(name) {

}
