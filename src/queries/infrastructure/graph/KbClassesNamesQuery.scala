package globus.queries.infrastructure.graph
import com.tinkerpop.blueprints.impls.orient.{OrientDynaElementIterable, OrientVertex}

import scala.collection.mutable.ArrayBuffer

class KbClassesNamesQuery extends GraphQueryable[ArrayBuffer[String]] {
  private val defaultClasses = Array("ORectangle", "OSchedule", "OPolygon", "OUser", "V", "OLineString", "OIdentity", "OFunction",
    "OMultiPolygon", "E", "OMultiPoint", "OPoint", "OTriggered")

  protected def getQuery = "select expand(classes) from metadata:schema"

  protected def map(resultSet: OrientDynaElementIterable) = {
    var classes = new ArrayBuffer[String]
    resultSet.forEach(v => {
        val name: String = v.asInstanceOf[OrientVertex].getProperty("name")
        classes += name
    })
    classes diff defaultClasses
  }

  protected def getFailMessage() = "Error until getting kb classes"
}
