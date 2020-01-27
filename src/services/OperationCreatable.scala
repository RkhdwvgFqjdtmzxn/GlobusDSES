package globus.services

import com.orientechnologies.orient.core.id.ORID
import globus.app.AppError
import globus.factories.constructContexts.OperationConstructContext
import globus.infrastructure.langApi.rop.R

trait OperationCreatable {
  def create(constructContext: OperationConstructContext): R[ORID, AppError]
}
