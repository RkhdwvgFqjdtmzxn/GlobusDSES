package globus.queries.infrastructure.graph

import com.orientechnologies.orient.core.id.ORID
import com.tinkerpop.blueprints.impls.orient.{OrientDynaElementIterable, OrientVertex}
import globus.domain.EdgeOperationType
import globus.domain.EdgeOperationType.EdgeOperationType

class EdgeOperationTypeIdQuery extends GraphContextQueryable[EdgeOperationType.EdgeOperationType, ORID] {
  protected var context: EdgeOperationType = _

  protected def getQuery: String = "select @rid from EdgeOperationType where value = '" + context + "'"

  protected def map(resultSet: OrientDynaElementIterable): ORID = {
    var id: ORID = null
    resultSet.forEach(v => {
      val item = v.asInstanceOf[OrientVertex]
      id = item.getIdentity
    })
    id
  }

  protected def getFailMessage(): String = "Error until getting edgeOperationType id"
}
