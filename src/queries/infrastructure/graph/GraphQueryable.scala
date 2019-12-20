package globus.queries.infrastructure.graph

import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.{OrientDynaElementIterable, OrientGraph}
import globus.infrastructure.graph.GraphError
import globus.infrastructure.graph.connection.Connection
import globus.infrastructure.langApi.rop._

trait GraphQueryable[T] {
  def get: R[T, GraphError] = {
    try {
      val graph: OrientGraph = Connection.tune
      val preResult: OrientDynaElementIterable = graph.command(new OCommandSQL(getQuery)).execute()
      val result = map(preResult)
      assert(result != null)
      succeed(result)
    } catch {
      case e: Exception => fail(new GraphError(getFailMessage))
    }
  }

  protected def getQuery: String

  protected def map(resultSet: OrientDynaElementIterable): T

  protected def getFailMessage(): String
}
