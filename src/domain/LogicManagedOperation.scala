package globus.domain
import com.orientechnologies.orient.core.id.ORID

import scala.collection.mutable.ArrayBuffer

class LogicManagedOperation(override val name: String, val logicalOperations: ArrayBuffer[LogicalOperation],
                            val execOperation: ChangingOperation)
    extends Operation(name) {
  var id: ORID = _
}
