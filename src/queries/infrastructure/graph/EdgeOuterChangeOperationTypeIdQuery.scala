package globus.queries.infrastructure.graph

import com.orientechnologies.orient.core.id.ORID
import com.tinkerpop.blueprints.impls.orient.{OrientDynaElementIterable, OrientVertex}
import globus.domain.EdgeOuterChangeOperationType
import globus.domain.EdgeOuterChangeOperationType.EdgeOuterChangeOperationType

class EdgeOuterChangeOperationTypeIdQuery extends GraphContextQueryable[EdgeOuterChangeOperationType.EdgeOuterChangeOperationType, ORID] {
  protected var context: EdgeOuterChangeOperationType = _

  protected def getQuery: String = "select @rid from EdgeOuterChangeOperationType where value = '" + context + "'"

  protected def map(resultSet: OrientDynaElementIterable): ORID = {
    var id: ORID = null
    resultSet.forEach(v => {
      val item = v.asInstanceOf[OrientVertex]
      id = item.getIdentity
    })
    id
  }

  protected def getFailMessage(): String = "Error until getting edgeOuterChangeOperationType id"
}
