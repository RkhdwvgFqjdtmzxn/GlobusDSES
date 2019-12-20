package globus.factories

import globus.infrastructure.langApi.rop.R

trait Factory[T] {
  def create(): R[T, FactoryError]
}
