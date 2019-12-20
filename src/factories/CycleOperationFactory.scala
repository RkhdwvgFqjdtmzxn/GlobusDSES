package globus.factories

import globus.domain.{CycleOperation, Operation}
import globus.infrastructure.langApi.rop._

class CycleOperationFactory (val name: String, val operation: Operation)
    extends Factory[CycleOperation] {
  def create(): R[CycleOperation, FactoryError] = {
    try {
      succeed(new CycleOperation(name, operation))
    } catch {
      case e: Exception => fail(new FactoryError("Error until creating cycleOperation."))
    }
  }
}
