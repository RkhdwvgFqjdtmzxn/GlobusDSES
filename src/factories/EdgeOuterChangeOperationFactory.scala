package globus.factories

import globus.domain.{EdgeOuterChangeOperation, Term}
import globus.infrastructure.langApi.rop._

class EdgeOuterChangeOperationFactory (val name: String,  val fromVertexTerm: Term, val toVertexTerm: Term) extends Factory[EdgeOuterChangeOperation] {

  def create(): R[EdgeOuterChangeOperation, FactoryError] = {
    try {
      succeed(new EdgeOuterChangeOperation(name, fromVertexTerm, toVertexTerm))
    } catch {
      case e: Exception => fail(new FactoryError("Error until creating edgeOuterChangeOperation."))
    }
  }
}
