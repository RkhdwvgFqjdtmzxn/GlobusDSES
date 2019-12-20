package globus.domain

import scala.collection.mutable.ArrayBuffer

class EdgeOuterChangeLogicalOperation(override val name: String, val fromVertexTerm: Term, val toVertexTerm: Term, val relatedTerms: Option[ArrayBuffer[Term]])
      extends Operation(name){

}
