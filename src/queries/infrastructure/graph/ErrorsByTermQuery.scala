package globus.queries.infrastructure.graph

import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable

import scala.collection.mutable.ArrayBuffer

//for deleting these errors by ids after inserting term with definition
class ErrorsByTermQuery extends GraphContextQueryable[String, ArrayBuffer[String]] {
  protected var context = ""

  protected def getQuery = ???

  protected def map(resultSet: OrientDynaElementIterable) = ???

  protected def getFailMessage() = ???
}
