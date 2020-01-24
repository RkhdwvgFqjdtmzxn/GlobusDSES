package globus.services

import globus.app.AppError

class ServiceError(val message: String) extends AppError{
}
