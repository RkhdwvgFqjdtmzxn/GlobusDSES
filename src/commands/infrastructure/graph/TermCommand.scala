package globus.commands.infrastructure.graph

import com.orientechnologies.orient.core.id.ORID
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import globus.commands.infrastructure.graph.internal.ChangeVertexContext
import globus.domain.{Term, TermType}
import globus.infrastructure.langApi.rop._

import scala.collection.mutable.ArrayBuffer
import scala.language.postfixOps

class TermCommand extends GraphTypeCommand[Term] {
  def createVertex(term: Term): OrientVertex = {
      val termVertex = graph addVertex(
        "class:Term",
        "name", term.name,
        "description", term.description
      )
      val termTypeVerts = graph.getVerticesOfClass("TermType").asInstanceOf[ArrayBuffer[OrientVertex]]
      if (termTypeVerts.nonEmpty) {
        val objTypeVertex = termTypeVerts.filter(_.getProperty("value") equals term.termType.toString).last
        if (objTypeVertex != null)
          objTypeVertex addEdge("HasInstance", termVertex)
        else {
          val termTypeCommand = new TermTypeCommand
          val typeVertexId = termTypeCommand addVertex term.termType.toString match {
            case Succ(data) => data
            case Fail(msg) => throw new Exception
          }
          RelateTypeVertexWithTerm(typeVertexId, termVertex)
        }
      } else {
        val termTypeCommand = new TermTypeCommand
        val typeObjId = termTypeCommand addVertex "obj" match {
          case Succ(data) => data
          case Fail(msg) => throw new Exception
        }
        val typeEventId = termTypeCommand addVertex "event" match {
          case Succ(data) => data
          case Fail(msg) => throw new Exception
        }
        val typeCharacteristicId = termTypeCommand addVertex "characteristic" match {
          case Succ(data) => data
          case Fail(msg) => throw new Exception
        }
        val typeVertexId = term.termType match {
          case TermType.obj => typeObjId
          case TermType.event => typeEventId
          case TermType.characteristic => typeCharacteristicId
        }
        RelateTypeVertexWithTerm(typeVertexId, termVertex)
      }
      termVertex
  }

  private def RelateTypeVertexWithTerm(typeVertexId: ORID, termVertex: OrientVertex) = {
    val typeVertex = graph getVertex typeVertexId
    typeVertex addEdge("HasInstance", termVertex)
  }

  def getAddFailMsg: String = "Error until creating new Term"

  def changeVertex(context: ChangeVertexContext) = ???
}