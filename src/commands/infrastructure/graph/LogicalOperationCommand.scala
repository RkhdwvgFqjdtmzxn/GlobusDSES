/*package globus.commands.infrastructure.graph

import com.orientechnologies.orient.core.id.ORID
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import globus.commands.infrastructure.graph.internal.ChangeVertexContext
import globus.domain.LogicalOperation
import globus.infrastructure.graph.GraphError
import globus.infrastructure.langApi.rop._
import globus.queries.infrastructure.graph.TermIdByNmeQuery

class LogicalOperationCommand extends GraphTypeCommand[LogicalOperation]{

  val termIdByNmeQuery = new TermIdByNmeQuery

  def addVertex(operation: LogicalOperation): R[ORID, GraphError] = {
    try {
      val operationVertex: OrientVertex = graph addVertex(
        "class: Operation",
        "name", operation.name
      )
      val relatedTerms = operation.relatedTerms.orNull
      if (relatedTerms != null) {
        for (term <- relatedTerms) {
          val termId = termIdByNmeQuery get term.name match {
            case Succ(data) => data
            case Fail(error) => return fail(error)
          }
          if (termId.isEmpty)
            return fail(new GraphError("There is trying of operation adding to KB, but related term (" + term.name + ") isn't exists in KB"))
          val termVertex = graph getVertex termId
          operationVertex addEdge("Relate with", termVertex)
        }
      }
      succeed(operationVertex getIdentity)
    } catch {
      case e: Exception => fail(new GraphError("Inner graph error during adding new operation."))
    }
  }

  def changeVertex(context: ChangeVertexContext): R[String, GraphError] = ???
}*/
