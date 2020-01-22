package globus.domain

import com.orientechnologies.orient.core.id.ORID

import scala.collection.mutable.ArrayBuffer

class TechProcess (val name: String) {
  var id: ORID = _

  var startOperation: Operation = _

  var operations: ArrayBuffer[ArrayBuffer[Operation]] = _
}
