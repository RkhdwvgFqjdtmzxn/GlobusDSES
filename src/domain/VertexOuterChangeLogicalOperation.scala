package globus.domain

import scala.collection.mutable.ArrayBuffer

class VertexOuterChangeLogicalOperation(override val name: String, override val vertexTerm: Term, val relatedTerms: Option[ArrayBuffer[Term]])
  extends VertexOuterOperationBase(name, vertexTerm)  {

}
