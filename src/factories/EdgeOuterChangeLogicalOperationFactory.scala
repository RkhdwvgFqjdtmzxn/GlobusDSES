package globus.factories

import globus.domain.{EdgeOuterChangeLogicalOperation, OperationPath, Term}
import globus.infrastructure.langApi.rop._

import scala.collection.mutable.ArrayBuffer

class EdgeOuterChangeLogicalOperationFactory(val name: String, val fromVertexTerm: Term, val toVertexTerm: Term,
                                             val relatedTerms: Option[ArrayBuffer[Term]])
            extends Factory[EdgeOuterChangeLogicalOperation] {
  def create(): R[EdgeOuterChangeLogicalOperation, FactoryError] = {
    try {
      succeed(new EdgeOuterChangeLogicalOperation(name, fromVertexTerm, toVertexTerm, relatedTerms))
    } catch {
      case e: Exception => fail(new FactoryError("Error until creating edgeOuterChangeLogicalOperation."))
    }
  }
}
