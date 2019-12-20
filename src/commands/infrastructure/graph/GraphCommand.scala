package globus.commands.infrastructure.graph

import com.orientechnologies.orient.core.id.ORID
import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.{OrientGraph, OrientVertex}
import globus.app.AppError
import globus.app.authority.CurrentUser
import globus.commands.infrastructure.graph.internal.DeleteVertexContext
import globus.infrastructure.graph.GraphError
import globus.infrastructure.graph.connection.Connection
import globus.infrastructure.langApi.rop._

import scala.language.postfixOps

class GraphCommand {
  val graph:OrientGraph = Connection.tune

  def addEdge(id1: ORID, id2: ORID, label: String) {
    val vertex1 = graph getVertex id1
    val vertex2 = graph getVertex id2
    vertex1 addEdge(label, vertex2)
  }

  def deleteVertex(id: String): R[ORID, AppError] = {
    try {
      val vertex: OrientVertex = getVertex(id)
      val vertexId: ORID = getVertexId(vertex)
      removeVertex(vertex)
      succeed(vertexId)
    } catch {
      case e: Exception => {
        val exMessage = if (CurrentUser.isProgramEngineer) {
          "\r\n\r\n" + e.getMessage
        } else "";
        fail(new GraphError("Inner graph error during deleting trnmt. "))
      }
    }
  }

  private def getVertex(id: String): OrientVertex = {
    graph getVertex id
  }

  private def getVertexId(vertex: OrientVertex): ORID = {
    vertex getIdentity
  }

  private def removeVertex(vertex: OrientVertex): Unit = {
    graph removeVertex vertex
  }

  def deleteVertexes(context: DeleteVertexContext): R[String, AppError] = {
    try {
      val vertexType: String = context.vertexType
      val idsPart: String = context.idsPart
      val command = buildCommand(vertexType, idsPart)
      graph.command(new OCommandSQL(command).execute())
      val idsList: String = getIdsList(idsPart)
      succeed(idsList)
    } catch {
      case e: Exception => {
        val exMessage = if (CurrentUser.isProgramEngineer) {
          "\r\n\r\n" + e.getMessage
        } else "";
        fail(new GraphError("Inner graph error during deleting trnmts. " + exMessage))
      }
    }
  }

  private def buildCommand(vertexType: String, idsPart: String): String = {
    val builder = StringBuilder.newBuilder
    builder.append("delete vertex ")
    builder.append(vertexType)
    builder.append(" where @r")
    builder.append(idsPart)
    builder toString
  }

  private def getIdsList(idsPart: String): String = {
    val startIdsIndex = idsPart.indexOf('#')
    val endIdsIndex = if (idsPart.contains("[")) idsPart.indexOf("]") else idsPart.length - 1
    idsPart.substring(startIdsIndex, endIdsIndex)
  }
}
