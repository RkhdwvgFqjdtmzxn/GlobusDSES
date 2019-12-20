package globus.factories

import globus.domain.{Term, VertexOuterChangeLogicalOperation}
import globus.infrastructure.langApi.rop._

import scala.collection.mutable.ArrayBuffer

class VertexOuterChangeLogicalOperationFactory(val name: String, val vertexTerm: Term, val relatedTerms: Option[ArrayBuffer[Term]])
          extends Factory[VertexOuterChangeLogicalOperation]{

  def create(): R[VertexOuterChangeLogicalOperation, FactoryError] = {
    try {
      succeed(new VertexOuterChangeLogicalOperation(name, vertexTerm, relatedTerms))
    } catch {
      case e: Exception => fail(new FactoryError("Error until creating vertexOuterChangeOperation."))
    }
  }
}
