package queries.infrastructure.graph

import com.orientechnologies.orient.core.id.ORID
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable
import globus.queries.infrastructure.graph.GraphContextQueryable

class OperationIdByNmeQuery extends GraphContextQueryable[String, ORID] {
  protected var context: String = _

  protected def getQuery: String = ???

  protected def map(resultSet: OrientDynaElementIterable): ORID = ???

  protected def getFailMessage(): String = ???
}
