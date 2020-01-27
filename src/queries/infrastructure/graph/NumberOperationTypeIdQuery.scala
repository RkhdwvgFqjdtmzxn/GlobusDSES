package globus.queries.infrastructure.graph

import com.orientechnologies.orient.core.id.ORID
import com.tinkerpop.blueprints.impls.orient.{OrientDynaElementIterable, OrientVertex}
import globus.domain.NumberOperationType
import globus.domain.NumberOperationType.NumberOperationType

class NumberOperationTypeIdQuery extends GraphContextQueryable[NumberOperationType.NumberOperationType, ORID]{
  protected var context: NumberOperationType = _

  protected def getQuery: String = "select @rid from NumberOperationType where value = '" + context + "'"

  protected def map(resultSet: OrientDynaElementIterable): ORID = {
    var id: ORID = null
    resultSet.forEach(v => {
      val item = v.asInstanceOf[OrientVertex]
      id = item.getIdentity
    })
    id
  }

  protected def getFailMessage(): String = "Error until getting numberOperationType id"
}
