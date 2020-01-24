package globus.queries.infrastructure.graph

import com.orientechnologies.orient.core.id.ORID
import com.tinkerpop.blueprints.impls.orient.{OrientDynaElementIterable, OrientVertex}

class TermIdByNmeQuery extends GraphContextQueryable[String, ORID]  {
  protected var context = ""

  protected def getQuery = "select from Term where name = '" + context + "'"

  protected def map(resultSet: OrientDynaElementIterable): ORID = {

    var id: ORID = null
    val terms = resultSet.forEach(v => {
      val item = v.asInstanceOf[OrientVertex]
      id = item.getIdentity
    })
    id
  }

  protected def getFailMessage() = "Error until getting term id by nme"
}
