package globus.queries.infrastructure.graph

import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.{OrientDynaElementIterable, OrientGraph}
import globus.infrastructure.graph.GraphError
import globus.infrastructure.graph.connection.Connection
import globus.infrastructure.langApi.rop._

trait GraphContextQueryable[TContext, TResult] {
  protected var context: TContext

  def get(context: TContext):R[TResult, GraphError] = {
    try {
      this.context = context
      val graph: OrientGraph = Connection.tune
      val query = getQuery
      val preResult: OrientDynaElementIterable = graph.command(new OCommandSQL(query)).execute()
      val result = map(preResult)
      assert(result != null)
      succeed(result)
    } catch {
      case e: Exception => fail(new GraphError(getFailMessage))
    }
  }

  protected def getQuery: String

  protected def map(resultSet: OrientDynaElementIterable): TResult

  protected def getFailMessage(): String
}
