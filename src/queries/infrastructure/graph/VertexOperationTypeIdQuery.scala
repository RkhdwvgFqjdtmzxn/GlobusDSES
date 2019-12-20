package globus.queries.infrastructure.graph

import com.orientechnologies.orient.core.id.ORID
import com.tinkerpop.blueprints.impls.orient.{OrientDynaElementIterable, OrientVertex}
import globus.domain.VertexOperationType
import globus.domain.VertexOperationType.VertexOperationType

class VertexOperationTypeIdQuery extends GraphContextQueryable[VertexOperationType.VertexOperationType, ORID] {
  protected var context: VertexOperationType = _

  protected def getQuery: String = "select @rid from VertexOperationType where value = '" + context + "'"

  protected def map(resultSet: OrientDynaElementIterable): ORID = {
    var id: ORID = null
    resultSet.forEach(v => {
      val item = v.asInstanceOf[OrientVertex]
      id = item.getIdentity
    })
    id
  }

  protected def getFailMessage(): String = "Error until getting vertexOperationType id"
}
