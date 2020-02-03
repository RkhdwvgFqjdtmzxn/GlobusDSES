/*package globus.queries.infrastructure.graph

import com.tinkerpop.blueprints.impls.orient.{OrientDynaElementIterable, OrientVertex}
import globus.domain.Operation

class OperationByIdQuery extends GraphContextQueryable[String, Operation] {
  protected var context: String = _

  protected def getQuery: String = "select from Operation where @rid = " + context

  protected def map(resultSet: OrientDynaElementIterable): Operation = {
    resultSet.forEach(v => {
      val item = v.asInstanceOf[OrientVertex]

      return new Operation() {}
    })
  }

  protected def getFailMessage(): String = ???
}*/
