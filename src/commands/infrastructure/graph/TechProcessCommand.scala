package globus.commands.infrastructure.graph

import com.orientechnologies.orient.core.id.ORID
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import globus.commands.infrastructure.graph.internal.ChangeVertexContext
import globus.domain.TechProcess
import globus.infrastructure.graph.GraphError
import globus.infrastructure.langApi.rop._

import scala.collection.mutable.ArrayBuffer

class TechProcessCommand (val termId: ORID, val applyingTermId: ORID, val startOpId: ORID, opIds: ArrayBuffer[ArrayBuffer[ORID]],
                          opValueNums: Option[Map[(Int, Int), (Int, Int)]] = None)
    extends GraphTypeCommand[TechProcess] {
  def addVertex(techProcess: TechProcess): R[ORID, GraphError] = {
    graph begin
    try {
      val techProcessVertex: OrientVertex = graph addVertex(
        "class:TechProcess",
        "name", techProcess.name
      )
      val techProcessTermVertex = graph getVertex termId
      techProcessTermVertex addEdge("HasKind", techProcessVertex)
      val techProcessApplyingTermVertex = graph getVertex applyingTermId
      techProcessVertex addEdge("Applying to", techProcessApplyingTermVertex)
      val startOpVertex = graph getVertex startOpId
      techProcessVertex addEdge("Op_0", startOpVertex)
      for ((opIdsArr, i) <- opIds.zipWithIndex) {
        var is1Elem = false
        if (opIdsArr.length <= 1)
          is1Elem = true
        for ((opId, j) <- opIdsArr.zipWithIndex) {
          val opVertex = graph getVertex opId
          techProcessVertex addEdge("Op_" + (i+1) + is1Elem match{case false => "_" + j; case true => ""}, opVertex)
          if (opValueNums.isDefined) {
            if (opValueNums.get.keySet.contains(i+1, j)) {
              val lastResultNums = opValueNums.get.filter(_._1 == (i+1, j))(i+1, j)
              techProcessVertex addEdge("Res_" + lastResultNums._1 + "_" + lastResultNums._2, opVertex)
            }
          }
        }
      }
      graph commit;
      succeed(techProcessVertex getIdentity)
    } catch {
      case e: Exception => {
        graph rollback;
        fail(new GraphError("Inner graph error during adding new techProcess."))
      }
    }
  }

  def changeVertex(context: ChangeVertexContext): R[String, GraphError] = ???
}
