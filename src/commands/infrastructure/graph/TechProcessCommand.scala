package globus.commands.infrastructure.graph

import com.orientechnologies.orient.core.id.ORID
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import globus.commands.infrastructure.graph.internal.ChangeVertexContext
import globus.domain.TechProcess
import globus.infrastructure.graph.GraphError
import globus.infrastructure.langApi.rop._

class TechProcessCommand (val termId: ORID) extends GraphTypeCommand[TechProcess] {
  def addVertex(techProcess: TechProcess): R[ORID, GraphError] = {
    try {
      val techProcessVertex: OrientVertex = graph addVertex(
        "class:TechProcess",
        "name", techProcess.name
      )
      val techProcessTermVertex = graph getVertex termId
      techProcessTermVertex addEdge("HasKind", techProcessVertex)
      succeed(techProcessVertex getIdentity)
    } catch {
      case e: Exception => fail(new GraphError("Inner graph error during adding new techProcess."))
    }
  }

  def changeVertex(context: ChangeVertexContext): R[String, GraphError] = ???
}
