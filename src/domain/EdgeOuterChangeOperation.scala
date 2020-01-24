package globus.domain
import com.orientechnologies.orient.core.id.ORID

class EdgeOuterChangeOperation(override val name: String, val fromVertexTerm: Term, val toVertexTerm: Term)
    extends Operation(name)  {
  var id: ORID = _
}
