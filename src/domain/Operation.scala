package globus.domain

import com.orientechnologies.orient.core.id.ORID

import scala.collection.mutable.ArrayBuffer

abstract class Operation (val name: String) {

  var id: ORID
}
