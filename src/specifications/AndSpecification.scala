package globus.specifications

class AndSpecification[T] (val spec1: Specification[T], val spec2: Specification[T]) extends Specification[T] {

  def isSatisfiedBy(value: T):SpecResult = {
    if (spec1 != null && spec2 != null) {
      val spec1Result = spec1.isSatisfiedBy(value)
      val spec2Result = spec2.isSatisfiedBy(value)
      if (spec1Result.hasMessage) {
        if (spec2Result.hasMessage)
          new SpecResult(spec1.isSatisfiedBy(value).value && spec2.isSatisfiedBy(value).value, spec1Result.message + "; \r\n" + spec2Result.message)
        else
          new SpecResult(spec1.isSatisfiedBy(value).value && spec2.isSatisfiedBy(value).value, spec1Result.message)
      }
      else{
        if (spec2Result.hasMessage)
          new SpecResult(spec1.isSatisfiedBy(value).value && spec2.isSatisfiedBy(value).value, spec2Result.message)
        else
          new SpecResult(spec1.isSatisfiedBy(value).value && spec2.isSatisfiedBy(value).value)
      }
    }
    else {
      new SpecResult(false)
    }
  }
}
