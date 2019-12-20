package globus.domain

import scala.collection.mutable.ArrayBuffer

abstract class Operation (val name: String) {
  var operationPaths: Option[ArrayBuffer[OperationPath]] = _
}
