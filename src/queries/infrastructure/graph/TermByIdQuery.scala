package globus.queries.infrastructure.graph

import com.orientechnologies.orient.core.record.impl.OVertexDocument
import com.tinkerpop.blueprints.impls.orient.{OrientDynaElementIterable, OrientVertex}
import globus.domain.{Term, TermType}

class TermByIdQuery extends GraphContextQueryable[String, Term] {
  protected var context = ""

  protected def getQuery: String = "select from (select in('HasInstance') as termType from Term unwind termType) where @rid = " + context

  protected def map(resultSet: OrientDynaElementIterable): Term = {
    resultSet.forEach(v => {
      val item = v.asInstanceOf[OrientVertex]
      val typeItem = item.getProperties.get("termType").asInstanceOf[OVertexDocument]
      val typeValue: String = typeItem.getProperty("value")
      val termType = typeValue match {
        case "obj" => TermType.obj
        case "event" => TermType.event
        case "characteristic" => TermType.characteristic
      }
      return new Term(item.getProperty("name"), termType, item.getProperty("description"))
    })
    null
  }

  protected def getFailMessage() = "Error until getting term by id"
}
