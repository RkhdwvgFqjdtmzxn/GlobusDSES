package globus.domain

import com.orientechnologies.orient.core.id.ORID

abstract class Operation (val name: String) {

  var id: ORID
}
