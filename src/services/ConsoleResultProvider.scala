package globus.services

import globus.app.AppError
import globus.infrastructure.langApi.rop.R

trait ConsoleResultProvider {

  def get(id:String) : R[String, AppError]
}
