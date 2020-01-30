package globus.services
import com.orientechnologies.orient.core.id.ORID
import globus.app.AppError
import globus.commands.infrastructure.graph.{EdgeInnerOperationCommand, EdgeOuterOperationCommand, VertexInnerOperationCommand, VertexOuterOperationCommand}
import globus.domain.{EdgeInnerChangeOperation, EdgeOuterChangeOperation, VertexInnerChangeOperation, VertexOuterChangeOperation}
import globus.factories.constructContexts.{EdgeInnerOperationConstructContext, EdgeOuterOperationConstructContext, LogicManagedOperationConstructContext, OperationConstructContext, VertexInnerOperationConstructContext, VertexOuterOperationConstructContext}
import globus.infrastructure.langApi.rop._

class OperationCreator extends OperationCreatable {
  def create(constructContext: OperationConstructContext): R[ORID, AppError] = {
    constructContext match {
      case vertexInner: VertexInnerOperationConstructContext => {
        val operation = new VertexInnerChangeOperation(vertexInner.name, vertexInner.term, vertexInner.vertexPropName)
        val command = new VertexInnerOperationCommand
        if (vertexInner.numberType != null)
          command.numberType = vertexInner.numberType
        command addVertex operation
      }
      case vertexOuter: VertexOuterOperationConstructContext => {
        val operation = new VertexOuterChangeOperation(vertexOuter.name, vertexOuter.term)
        val command = new VertexOuterOperationCommand(vertexOuter.vertexOuterOperationType)
        command addVertex operation
      }
      case edgeInner: EdgeInnerOperationConstructContext => {
        val operation = new EdgeInnerChangeOperation(edgeInner.name, edgeInner.fromTerm, edgeInner.toTerm)
        val command = new EdgeInnerOperationCommand
        command addVertex operation
      }
      case edgeOuter: EdgeOuterOperationConstructContext => {
        val operation = new EdgeOuterChangeOperation(edgeOuter.name, edgeOuter.fromTerm, edgeOuter.toTerm)
        val command = new EdgeOuterOperationCommand(edgeOuter.edgeOuterOperationType)
        command addVertex operation
      }
      case logicManaged: LogicManagedOperationConstructContext => {
        val
      }
    }
  }
}
