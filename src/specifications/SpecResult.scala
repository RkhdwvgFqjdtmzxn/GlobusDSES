package globus.specifications

class SpecResult(val value: Boolean)  {
  var message = ""

  def this(value: Boolean, message: String){
    this(value)
    this.message = message
  }

  def hasMessage = message.nonEmpty
}
