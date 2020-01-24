package globus.domain
import com.orientechnologies.orient.core.id.ORID

class VertexInnerChangeOperation(override val name: String, val vertexTerm: Term, val vertexPropName: String)
    extends Operation(name) {
  var vertexPropValue: String = _
  var id: ORID = _
}
