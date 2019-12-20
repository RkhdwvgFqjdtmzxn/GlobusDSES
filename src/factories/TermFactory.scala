package globus.factories

import globus.domain.{Term, TermType}
import globus.infrastructure.langApi.rop._

import scala.collection.mutable.ArrayBuffer

class TermFactory extends UIManagedFactory[Term] {
  def createFrom(paramsList: ArrayBuffer[String]): R[Term, FactoryError] = {
    try {
      val termType = paramsList(1) match {
        case "obj" => TermType.obj
        case "characteristic" => TermType.characteristic
        case "event" => TermType.event
        case _ => return fail(new FactoryError("Incorrect value of termType in creating term " + paramsList(0)))
      }
      succeed(new Term(paramsList(0), termType, paramsList(2)))
    } catch {
      case e: Exception => fail(new FactoryError("Error until created new term"))
    }
  }
}
