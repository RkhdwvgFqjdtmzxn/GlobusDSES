package globus.factories

import globus.domain.TechProcess
import globus.infrastructure.langApi.rop._

import scala.collection.mutable.ArrayBuffer

class TechProcessFactory(name: String, pathNumbers: Option[ArrayBuffer[Int]]) extends Factory[TechProcess]{
  def create(): R[TechProcess, FactoryError] = {
    try {
      succeed(new TechProcess(name, pathNumbers))
    } catch {
      case e: Exception => fail(new FactoryError("Error until created new techProcess"))
    }
  }
}
