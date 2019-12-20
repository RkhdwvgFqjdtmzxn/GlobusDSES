package globus.queries.infrastructure.graph

import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable
import globus.domain.Term

import scala.collection.mutable.ArrayBuffer

class TermsQuery extends GraphQueryable[ArrayBuffer[Term]]{
  protected def getQuery = ???

  protected def map(resultSet: OrientDynaElementIterable) = ???

  protected def getFailMessage() = ???
}
