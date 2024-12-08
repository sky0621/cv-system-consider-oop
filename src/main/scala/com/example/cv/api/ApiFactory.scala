package com.example.cv.api

object ApiFactory {
  def create[F[_], E <: ApiError, R <: Response](
      apiName: String
  ): Api[F, E, R] = apiName match {
    case "applyForCVRegistration" =>
      new ApplyForCVRegistrationApi().asInstanceOf[Api[F, E, R]]
    case _ => throw new IllegalArgumentException(apiName)
  }
}
