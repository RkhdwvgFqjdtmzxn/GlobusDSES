package globus.domain

import com.orientechnologies.orient.core.id.ORID
import globus.domain.internal.CorrectValuesSet

import scala.collection.mutable.ArrayBuffer

class LogicalOperation(override val name: String, val relatedTerms: Option[ArrayBuffer[Term]], val propNames: Option[ArrayBuffer[String]],
                       val correctValuesSet: CorrectValuesSet)
  extends Operation(name)  {
  var id: ORID = _
}
