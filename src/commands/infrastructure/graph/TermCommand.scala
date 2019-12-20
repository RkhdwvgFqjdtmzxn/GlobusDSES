package globus.commands.infrastructure.graph

import com.orientechnologies.orient.core.id.ORID
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import globus.commands.infrastructure.graph.internal.ChangeVertexContext
import globus.domain.Term
import globus.infrastructure.graph.GraphError
import globus.infrastructure.langApi.rop._

import scala.language.postfixOps
import scala.collection.mutable.ArrayBuffer

class TermCommand extends GraphTypeCommand[Term] {
  def addVertex(term: Term): R[ORID, GraphError] = {
    try {
      val termVertex = graph addVertex(
        "class:Term",
        "name", term.name,
        "description", term.description
      )
      val termTypeVerts = graph.getVerticesOfClass("TermType").asInstanceOf[ArrayBuffer[OrientVertex]]
      val objTypeVertex = termTypeVerts.filter(_.getProperty("value") equals term.termType).last
      objTypeVertex addEdge("HasInstance", termVertex)
      succeed(termVertex getIdentity)
    } catch {
      case e: Exception => fail(new GraphError("Inner graph error during adding new term."))
    }
  }

  def changeVertex(context: ChangeVertexContext) = ???
}