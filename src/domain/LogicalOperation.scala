package globus.domain

import scala.collection.mutable.ArrayBuffer

class LogicalOperation(override val name: String, var relatedTerms: Option[ArrayBuffer[Term]])
  extends Operation(name)  {
}
