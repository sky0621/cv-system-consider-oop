package com.example.cv.domain

object Message {
  case class ApprovedCVRegistrationMessage(value: String)

  case class RejectedCVRegistrationMessage(value: String)
}

object Name {
  case class UnvalidatedName(givenName: String, familyName: String) {
    def isValid: Boolean = givenName.nonEmpty && familyName.nonEmpty
  }

  sealed trait VerifiedName

  case class ValidatedName(givenName: String, familyName: String)
    extends VerifiedName

  case class InvalidName(givenName: String, familyName: String)
    extends VerifiedName
}

object Birthday {
  case class UnvalidatedBirthday(year: Int, month: Int, day: Int) {
    // @formatter:off
    def isValid: Boolean =
      year > 1900 &&
      month >= 1 && month <= 12 &&
      day >= 1 && day <= 31
    // @formatter:on
  }

  sealed trait VerifiedBirthday

  case class ValidatedBirthday(year: Int, month: Int, day: Int)
    extends VerifiedBirthday

  case class InvalidBirthday(year: Int, month: Int, day: Int)
    extends VerifiedBirthday
}

object MailAddress {
  case class UnvalidatedMailAddress(value: String) {
    def isValid: Boolean =
      "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$".r.matches(value)
  }

  sealed trait VerifiedMailAddress

  case class ValidatedMailAddress(value: String) extends VerifiedMailAddress

  case class InvalidMailAddress(value: String) extends VerifiedMailAddress
}
