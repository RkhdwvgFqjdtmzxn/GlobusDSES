package globus.domain

import domain.internal.CorrectValuesSet

import scala.collection.mutable.ArrayBuffer

class VertexInnerChangeLogicalOperation(override val name: String, override val vertexTerm: Term, val relatedTerms: Option[ArrayBuffer[Term]],
                                        val propNames: Option[ArrayBuffer[String]], val correctValuesSet: CorrectValuesSet)
    extends VertexInnerChangeOperationBase(name, vertexTerm)  {

}
