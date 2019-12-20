package globus.domain

import scala.collection.mutable.ArrayBuffer

class OperationPath() {
  var nextOperations: Option[ArrayBuffer[Operation]] = _
}
