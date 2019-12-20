package globus.extensions

object SpecificationExtensions {
  implicit class ExtendedSpecification(val value: String){
    def and(pairSpec: String): String = ""
  }
}
