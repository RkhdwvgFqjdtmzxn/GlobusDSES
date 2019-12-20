package globus.infrastructure.graph.connection

import com.tinkerpop.blueprints.impls.orient.OrientGraph

object Connection {

  var graph: Option[OrientGraph] = None

  def tune:OrientGraph = {
    if (graph.isEmpty) {
      //OrientBaseGraph.getActiveGraph.shutdown()
      val uri = "plocal:/opt/orientdb/orientdb-3.0.17/databases/Saturn/"
      // val factory = new OrientGraphFactory(uri, "root", "ORIENT", false)
      val result = new OrientGraph(uri, "admin", "admin", false)
      //factory.getTx
      graph = Some(result)
      result
    }
    graph.get
  }

  def close = {
    if (graph.isDefined){
      graph.get.shutdown()
    }
  }
}