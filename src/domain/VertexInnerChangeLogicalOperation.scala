package globus.domain

import scala.collection.mutable.ArrayBuffer

class VertexInnerChangeLogicalOperation(override val name: String, val vertexTerm: Term, val relatedTerms: Option[ArrayBuffer[Term]] )
    extends Operation(name)  {

}
