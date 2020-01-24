package globus.domain
import com.orientechnologies.orient.core.id.ORID

class VertexOuterChangeOperation(override val name: String, val vertexTerm: Term) extends Operation(name) {
  var id: ORID = _
}
