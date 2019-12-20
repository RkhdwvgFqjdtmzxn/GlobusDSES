package globus.factories

import globus.app.AppError

class FactoryError (val errMessage: String) extends AppError {
  def message = errMessage
}
