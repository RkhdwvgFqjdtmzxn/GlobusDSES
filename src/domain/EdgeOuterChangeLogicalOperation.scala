package globus.domain

import scala.collection.mutable.ArrayBuffer

class EdgeOuterChangeLogicalOperation(override val name: String, override val fromVertexTerm: Term, override val toVertexTerm: Term,
                                      val relatedTerms: Option[ArrayBuffer[Term]])
      extends EdgeOperationBase(name, fromVertexTerm, toVertexTerm){

}
