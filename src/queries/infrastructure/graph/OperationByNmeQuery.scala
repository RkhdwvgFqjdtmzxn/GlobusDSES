package globus.queries.infrastructure.graph
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable

class OperationByNmeQuery extends GraphContextQueryable[String, String] {
  protected var context: String = _

  protected def getQuery: String = ???

  protected def map(resultSet: OrientDynaElementIterable): String = ???

  protected def getFailMessage(): String = ???
}
