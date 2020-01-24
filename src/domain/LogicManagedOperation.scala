package globus.domain
import com.orientechnologies.orient.core.id.ORID

import scala.collection.mutable.ArrayBuffer

class LogicManagedOperation(override val name: String) extends Operation(name) {
  var id: ORID = _

  var logicalOperations: Option[ArrayBuffer[LogicalOperation]] = _

  var execOperation: Operation = _
}
