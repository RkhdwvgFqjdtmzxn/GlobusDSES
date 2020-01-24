package globus.factories.constructContexts

import globus.domain.Term

import scala.collection.mutable.ArrayBuffer

class LogicalOperationConstructContext(override val name: String, val relatedTerm: Term) extends OperationConstructContext(name){
  var edgedTerm: Term = _
}