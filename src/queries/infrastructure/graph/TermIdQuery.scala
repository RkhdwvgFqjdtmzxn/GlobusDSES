package globus.queries.infrastructure.graph
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable

class TermIdQuery extends GraphContextQueryable[String, String]  {
  protected var context = ""

  protected def getQuery = ???

  protected def map(resultSet: OrientDynaElementIterable) = ???

  protected def getFailMessage() = ???
}
