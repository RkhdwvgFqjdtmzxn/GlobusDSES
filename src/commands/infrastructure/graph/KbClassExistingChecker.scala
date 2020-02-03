package globus.commands.infrastructure.graph

import com.orientechnologies.orient.core.metadata.schema.OType
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import globus.infrastructure.graph.connection.Connection
import globus.infrastructure.langApi.rop
import globus.queries.infrastructure.graph.KbClassesNamesQuery

import scala.language.postfixOps

class KbClassExistingChecker {
  private val graph:OrientGraph = Connection.tune

  private val classesNamesQuery = new KbClassesNamesQuery

  def check(className: String) = {
    val existClasses = classesNamesQuery get match {
      case rop.Succ(data) => data
      case rop.Fail(msg) => throw new Exception
    }
    if (!(existClasses contains className)){
      graph createVertexType className
      if (className == "Term")
        createTermProps()
      if (className == "TermType")
        createTermTypeProps()
    }
  }

  private def createTermProps() {
    val termClass = graph getVertexType "Term"
    termClass.createProperty("name", OType.STRING).setNotNull(true)
    termClass.createProperty("description", OType.STRING).setNotNull(true)
  }

  private def createTermTypeProps() {
    val termTypeClass = graph getVertexType "TermType"
    termTypeClass.createProperty("value", OType.STRING).setNotNull(true)
  }
}
