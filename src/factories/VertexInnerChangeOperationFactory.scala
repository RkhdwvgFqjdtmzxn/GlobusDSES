package globus.factories

import globus.domain.{OperationPath, Term, VertexInnerChangeOperation}
import globus.infrastructure.langApi.rop.{R, fail, succeed}

import scala.collection.mutable.ArrayBuffer

class VertexInnerChangeOperationFactory (val name: String, val vertexTerm: Term) extends Factory[VertexInnerChangeOperation] {
  def create(): R[VertexInnerChangeOperation, FactoryError] = {
    try {
      succeed(new VertexInnerChangeOperation(name, vertexTerm))
    } catch {
      case e: Exception => fail(new FactoryError("Error until creating vertexInnerChangeOperation."))
    }
  }
}
