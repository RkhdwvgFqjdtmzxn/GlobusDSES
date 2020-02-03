package globus.infrastructure.graph.connection

import com.tinkerpop.blueprints.impls.orient.OrientGraph

object Connection {

  var graph: Option[OrientGraph] = _

  def setGraph(graph: Option[OrientGraph]) = {
    if (graph.isEmpty) {
      this.graph = graph
      //OrientBaseGraph.getActiveGraph.shutdown()
      //val uri = "plocal:/opt/orientdb/orientdb-3.0.17/databases/Saturn/"
      // val factory = new OrientGraphFactory(uri, "root", "ORIENT", false)
      //val result = new OrientGraph(uri, "admin", "admin", false)
      //factory.getTx
      //result
    }
  }

  def tune(): OrientGraph = {
    if (graph.isDefined)
      graph.get
    else {
      val uri = "plocal:/opt/orientdb/orientdb-3.0.17/databases/Saturn/"
      val result = new OrientGraph(uri, "admin", "admin", false)
      result
    }
  }

  def close(graph: Option[OrientGraph]) = {
    if (graph.isDefined){
      graph.get.shutdown()
    }
  }
}