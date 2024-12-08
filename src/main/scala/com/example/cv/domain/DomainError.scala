package com.example.cv.domain

trait DomainError {
  def message: String
}

case class UnexpectedError(cause: Cause) extends DomainError {
  override def message: String = cause.message.toString
}
object UnexpectedError {
  def apply(message: String): UnexpectedError = UnexpectedError(
    Cause(SimpleErrorMessage(message), None)
  )
}

case class Cause(message: DomainErrorMessage, causeOpt: Option[Throwable])

trait DomainErrorMessage

case class SimpleErrorMessage(message: String) extends DomainErrorMessage
