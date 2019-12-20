package globus.queries.infrastructure.graph

import com.orientechnologies.orient.core.id.ORID
import com.tinkerpop.blueprints.impls.orient.{OrientDynaElementIterable, OrientVertex}
import globus.domain.VertexOuterChangeOperationType
import globus.domain.VertexOuterChangeOperationType.VertexOuterChangeOperationType

class VertexOuterChangeOperationTypeIdQuery extends GraphContextQueryable[VertexOuterChangeOperationType.VertexOuterChangeOperationType, ORID] {
  protected var context: VertexOuterChangeOperationType = _

  protected def getQuery: String = "select @rid from VertexOuterChangeOperationType where value = '" + context + "'"

  protected def map(resultSet: OrientDynaElementIterable): ORID = {
    var id: ORID = null
    resultSet.forEach(v => {
      val item = v.asInstanceOf[OrientVertex]
      id = item.getIdentity
    })
    id
  }

  protected def getFailMessage(): String = "Error until getting vertexOuterChangeOperationType id"
}
