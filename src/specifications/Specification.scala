package globus.specifications

trait Specification[T] {

  def isSatisfiedBy(value: T): SpecResult
}
