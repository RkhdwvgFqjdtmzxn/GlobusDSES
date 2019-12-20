package globus.factories

import globus.domain.{Term, VertexOuterChangeOperation}
import globus.infrastructure.langApi.rop._

class VertexOuterChangeOperationFactory(val name: String, val vertexTerm: Term) extends Factory[VertexOuterChangeOperation] {
  def create(): R[VertexOuterChangeOperation, FactoryError] = {
    try {
      succeed(new VertexOuterChangeOperation(name, vertexTerm))
    } catch {
      case e: Exception => fail(new FactoryError("Error until creating vertexOuterChangeOperation."))
    }
  }
}
