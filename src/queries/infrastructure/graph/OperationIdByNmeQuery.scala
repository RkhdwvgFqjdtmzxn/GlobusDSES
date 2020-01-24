package queries.infrastructure.graph

import com.orientechnologies.orient.core.id.ORID
import com.tinkerpop.blueprints.impls.orient.{OrientDynaElementIterable, OrientVertex}
import globus.queries.infrastructure.graph.GraphContextQueryable

class OperationIdByNmeQuery extends GraphContextQueryable[String, ORID] {
  protected var context: String = ""

  protected def getQuery: String = "select from Operation where name = '" + context + "'"

  protected def map(resultSet: OrientDynaElementIterable): ORID = {
    var id: ORID = null
    val terms = resultSet.forEach(v => {
      val item = v.asInstanceOf[OrientVertex]
      id = item.getIdentity
    })
    id
  }

  protected def getFailMessage(): String = "Error until getting operation id by nme"
}
