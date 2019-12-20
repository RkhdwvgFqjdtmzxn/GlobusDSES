package globus.factories

import globus.infrastructure.langApi.rop.R

import scala.collection.mutable.ArrayBuffer

trait UIManagedFactory[T] {
  def createFrom(paramsList: ArrayBuffer[String]): R[T, FactoryError]
}
