package globus.services
import com.orientechnologies.orient.core.id.ORID
import globus.app.AppError
import globus.commands.infrastructure.graph.VertexInnerOperationCommand
import globus.domain.VertexInnerChangeOperation
import globus.factories.constructContexts.{OperationConstructContext, VertexInnerOperationConstructContext}
import globus.infrastructure.langApi.rop._

class OperationCreator extends OperationCreatable {
  def create(constructContext: OperationConstructContext): R[ORID, AppError] = {
    constructContext match {
      case vertexInner: VertexInnerOperationConstructContext => {
        val operation = new VertexInnerChangeOperation(vertexInner.name, vertexInner.term, vertexInner.vertexPropName)
        val command: VertexInnerOperationCommand = vertexInner.numberType match {

        }
      }
    }
  }
}
