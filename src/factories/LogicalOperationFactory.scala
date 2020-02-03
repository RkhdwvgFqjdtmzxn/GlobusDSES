/*package globus.factories

import globus.domain.{LogicalOperation, Term}
import globus.infrastructure.langApi.rop._

import scala.collection.mutable.ArrayBuffer

class LogicalOperationFactory(val name: String, var relatedTerms: Option[ArrayBuffer[Term]]) extends Factory[LogicalOperation] {
  def create(): R[LogicalOperation, FactoryError] = {
    try {
      succeed(new LogicalOperation(name, relatedTerms))
    } catch {
      case e: Exception => fail(new FactoryError("Error until creating logicalOperation."))
    }
  }
}*/
