package globus.domain

abstract class TermKind(val name: String, val definition: String) {
  var error: Option[Error] = _
}