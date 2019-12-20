package globus.commands.infrastructure.graph

import com.orientechnologies.orient.core.id.ORID
import globus.infrastructure.graph.GraphError
import globus.infrastructure.langApi.rop.R

import scala.collection.mutable.ArrayBuffer

trait SubGraphCreator {
  def work(termId: ORID): R[ArrayBuffer[ORID], GraphError]
}