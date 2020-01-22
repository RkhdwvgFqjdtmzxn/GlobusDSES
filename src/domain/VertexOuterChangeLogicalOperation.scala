package globus.domain

import domain.internal.CorrectValuesSet

import scala.collection.mutable.ArrayBuffer

class VertexOuterChangeLogicalOperation(override val name: String, override val vertexTerm: Term, val relatedTerms: Option[ArrayBuffer[Term]],
                                        val propNames: Option[ArrayBuffer[String]], val correctValuesSet: CorrectValuesSet)
  extends VertexOuterOperationBase(name, vertexTerm)  {

}
