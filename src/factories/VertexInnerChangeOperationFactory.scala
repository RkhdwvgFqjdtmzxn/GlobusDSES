package globus.factories

import globus.domain.{Term, VertexInnerChangeOperation}
import globus.infrastructure.langApi.rop.{R, fail, succeed}

class VertexInnerChangeOperationFactory (val name: String, val vertexTerm: Term) extends Factory[VertexInnerChangeOperation] {
  def create(): R[VertexInnerChangeOperation, FactoryError] = {
    try {
      succeed(new VertexInnerChangeOperation(name, vertexTerm))
    } catch {
      case e: Exception => fail(new FactoryError("Error until creating vertexInnerChangeOperation."))
    }
  }
}
