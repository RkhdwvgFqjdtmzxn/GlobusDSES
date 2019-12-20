package globus.domain

import scala.collection.mutable.ArrayBuffer

class VertexOuterChangeLogicalOperation(override val name: String, val vertexTerm: Term, val relatedTerms: Option[ArrayBuffer[Term]])
  extends Operation(name)  {

}
