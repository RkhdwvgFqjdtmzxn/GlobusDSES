package globus.commands.infrastructure.graph

import com.orientechnologies.orient.core.id.ORID
import com.tinkerpop.blueprints.impls.orient.{OrientGraph, OrientVertex}
import globus.commands.infrastructure.graph.internal.ChangeVertexContext
import globus.infrastructure.graph.GraphError
import globus.infrastructure.graph.connection.Connection
import globus.infrastructure.langApi.rop._
import scala.language.postfixOps

abstract class GraphTypeCommand[T] {
  val graph:OrientGraph = Connection.tune

  def addVertex(vertex: T): R[ORID, GraphError] = {
    graph.begin();
    try{
      val graphVertex = createVertex(vertex)
      graph.commit();
      succeed(graphVertex getIdentity)
    } catch {
      case e: Exception => {
        graph.rollback();
        fail(new GraphError(getAddFailMsg))
      }
    }
  }

  def createVertex(vertex: T): OrientVertex

  def getAddFailMsg: String

  def changeVertex(context: ChangeVertexContext): R[String, GraphError]
}
