package globus.commands.infrastructure.graph

import com.orientechnologies.orient.core.id.ORID
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import globus.infrastructure.graph.GraphError
import globus.infrastructure.graph.connection.Connection
import globus.infrastructure.langApi.rop._

import scala.language.postfixOps

class TermTypeCommand {
  private val graph:OrientGraph = Connection.tune

  def addVertex(value: String): R[ORID, GraphError] = {
    graph.begin();
    try {
      val termTypeVertex = graph addVertex("class: TermType", "value", value)
      graph.commit();
      succeed(termTypeVertex getIdentity)
    } catch {
      case e: Exception => {
        graph.rollback();
        fail(new GraphError("Error until creating new TermType"))
      }
    }
  }
}
