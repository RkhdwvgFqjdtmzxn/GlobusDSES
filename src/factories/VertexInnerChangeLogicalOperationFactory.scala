package globus.factories

import globus.domain.{OperationPath, Term, VertexInnerChangeLogicalOperation}
import globus.infrastructure.langApi.rop._

import scala.collection.mutable.ArrayBuffer

class VertexInnerChangeLogicalOperationFactory(val name: String, val vertexTerm: Term, val relatedTerms: Option[ArrayBuffer[Term]])
    extends Factory[VertexInnerChangeLogicalOperation] {
  def create(): R[VertexInnerChangeLogicalOperation, FactoryError] = {
    try {
      succeed(new VertexInnerChangeLogicalOperation(name, vertexTerm, relatedTerms))
    } catch {
      case e: Exception => fail(new FactoryError("Error until creating vertexInnerChangeLogicalOperation."))
    }
  }
}
