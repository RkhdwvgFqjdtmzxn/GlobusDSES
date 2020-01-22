package globus.factories.constructContexts

import globus.domain.Term

import scala.collection.mutable.ArrayBuffer

class LogicalOperationConstructContext(override val name: String, val terms: ArrayBuffer[Term]) extends OperationConstructContext(name){

}