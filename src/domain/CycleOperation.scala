package globus.domain
import com.orientechnologies.orient.core.id.ORID

class CycleOperation(override val name: String, val operation: Operation)
  extends Operation(name)  {
  var id: ORID = _
}
