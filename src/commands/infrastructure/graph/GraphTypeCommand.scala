package globus.commands.infrastructure.graph

import com.orientechnologies.orient.core.id.ORID
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import globus.commands.infrastructure.graph.internal.ChangeVertexContext
import globus.infrastructure.graph.GraphError
import globus.infrastructure.graph.connection.Connection
import globus.infrastructure.langApi.rop.R

trait GraphTypeCommand[T] {
  val graph:OrientGraph = Connection.tune

  def addVertex(vertex: T): R[ORID, GraphError]

  def changeVertex(context: ChangeVertexContext): R[String, GraphError]
}
