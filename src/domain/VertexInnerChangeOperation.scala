package globus.domain
import com.orientechnologies.orient.core.id.ORID

class VertexInnerChangeOperation(override val name: String, val vertexTerm: Term, val vertexPropName: String)
    extends ChangingOperation(name) {
  var id: ORID = _
}
