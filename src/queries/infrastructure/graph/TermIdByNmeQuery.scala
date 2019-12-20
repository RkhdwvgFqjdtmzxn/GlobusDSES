package globus.queries.infrastructure.graph

import com.tinkerpop.blueprints.impls.orient.{OrientDynaElementIterable, OrientVertex}
import globus.domain.{Term, TermType}

class TermIdByNmeQuery extends GraphContextQueryable[String, String]  {
  protected var context = ""

  protected def getQuery = "select from Term where name = '" + context + "'"

  protected def map(resultSet: OrientDynaElementIterable): String = {

    var id = ""
    val terms = resultSet.forEach(v => {
      val item = v.asInstanceOf[OrientVertex]
      id = item.getIdentity.toString
    })
    id
  }

  protected def getFailMessage() = "Error until getting term by nme"
}
