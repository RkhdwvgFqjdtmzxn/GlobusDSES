package globus.domain

import scala.collection.mutable.ArrayBuffer

class TechProcess (val name: String, val startOperation: Operation, val operations: ArrayBuffer[ArrayBuffer[Operation]]) {

}
