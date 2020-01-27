package globus.domain
import com.orientechnologies.orient.core.id.ORID

class EdgeOuterChangeOperation(override val name: String, val fromVertexTerm: Term, val toVertexTerm: Term)
    extends ChangingOperation(name)  {
  var id: ORID = _
}
