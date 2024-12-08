package com.example.cv.domain

import Birthday.{UnvalidatedBirthday, ValidatedBirthday, VerifiedBirthday}
import MailAddress.{
  InvalidMailAddress,
  UnvalidatedMailAddress,
  ValidatedMailAddress,
  VerifiedMailAddress
}
import Message._
import Name.{UnvalidatedName, ValidatedName, VerifiedName}

trait Event

case class SavedApplyForCVRegistrationEvent(
    maybeName: UnvalidatedName,
    maybeBirthday: UnvalidatedBirthday,
    maybeMailAddress: UnvalidatedMailAddress
) extends Event

trait VerifiedCVRegistrationEvent extends Event

case class ApprovedCVRegistrationEvent(
    validatedName: ValidatedName,
    validatedBirthday: ValidatedBirthday,
    validatedMailAddress: ValidatedMailAddress
) extends VerifiedCVRegistrationEvent

case class RejectedCVRegistrationEvent(
    verifiedName: VerifiedName,
    verifiedBirthday: VerifiedBirthday,
    verifiedMailAddress: VerifiedMailAddress
) extends VerifiedCVRegistrationEvent

trait NotifiedCVRegistrationEvent extends Event

case class NotifiedApprovedCVRegistrationEvent(
    validatedMailAddress: ValidatedMailAddress,
    approvedCVRegistrationMessage: ApprovedCVRegistrationMessage
) extends NotifiedCVRegistrationEvent

case class NotifiedRejectedCVRegistrationEvent(
    invalidMailAddress: InvalidMailAddress,
    rejectedCVRegistrationMessage: RejectedCVRegistrationMessage
) extends NotifiedCVRegistrationEvent
