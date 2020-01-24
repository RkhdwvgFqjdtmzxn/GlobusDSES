package globus.domain

import com.orientechnologies.orient.core.id.ORID

abstract class LogicalOperation(override val name: String, val relatedTerm: Term)
    extends Operation(name)  {
  var id: ORID = _
}
