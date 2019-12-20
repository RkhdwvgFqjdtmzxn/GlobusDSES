package globus.domain

class CycleOperation(override val name: String, val operation: Operation)
  extends Operation(name)  {

  var iterationsCount: Int = _

  var logicalOperation: LogicalOperation = _
}
