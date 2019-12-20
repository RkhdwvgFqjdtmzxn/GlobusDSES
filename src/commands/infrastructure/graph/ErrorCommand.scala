package globus.commands.infrastructure.graph
import globus.commands.infrastructure.graph.internal.ChangeVertexContext

class ErrorCommand extends GraphTypeCommand[Error] {
  def addVertex(vertex: Error) = ???

  def changeVertex(context: ChangeVertexContext) = ???
}
