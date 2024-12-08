package com.example.cv.domain

import cats.Applicative
import cats.data.EitherT
import com.example.cv.oop.implementation.domain.Birthday.UnvalidatedBirthday
import com.example.cv.oop.implementation.domain.MailAddress.{InvalidMailAddress, UnvalidatedMailAddress, ValidatedMailAddress}
import com.example.cv.oop.implementation.domain.Message._
import com.example.cv.oop.implementation.domain.Name.UnvalidatedName
import com.example.cv.oop.implementation.{InMemoryDatabase, domain}

import scala.concurrent.ExecutionContext

trait CommandTrait[F[_], E <: Event] {
  def execute(): EitherT[F, DomainError, E]
}

// CV登録を申請する
class ApplyForCVRegistrationCommand[F[_]: Applicative] {
  def execute(
      maybeName: UnvalidatedName,
      maybeBirthday: UnvalidatedBirthday,
      maybeMailAddress: UnvalidatedMailAddress
  ): EitherT[F, DomainError, AppliedForCVRegistrationEvent] = {
    val mailOpt = Option.when(maybeMailAddress.value.nonEmpty)(maybeMailAddress)
    for {
      _ <- InMemoryDatabase
        .unvalidatedMailAddressStorage[F]
        .save(mailOpt)
        .leftMap[DomainError](e => UnexpectedError(e))
    } yield AppliedForCVRegistrationEvent(
      maybeName,
      maybeBirthday,
      maybeMailAddress
    )
  }
}

// CV登録申請内容を検証する
case class VerifyCVRegistrationCommandTrait[F[_]: Applicative](
    maybeMailAddress: UnvalidatedMailAddress
)(implicit ec: ExecutionContext)
    extends CommandTrait[F, VerifiedCVRegistrationEvent] {

  override def execute()
      : EitherT[F, DomainError, VerifiedCVRegistrationEvent] = {
    if (maybeMailAddress.isValid) {
      EitherT.rightT[F, DomainError] {
        ApprovedCVRegistrationEvent(
          ValidatedMailAddress(maybeMailAddress.value)
        )
      }
    } else {
      EitherT.rightT[F, DomainError] {
        RejectedCVRegistrationEvent(
          InvalidMailAddress(maybeMailAddress.value)
        )
      }
    }
  }
}

// CV登録申請承認結果を通知する
case class NotifyApprovedCVRegistrationResultCommandTrait[F[_]: Applicative](
    validatedMailAddress: ValidatedMailAddress
)(implicit ec: ExecutionContext)
    extends CommandTrait[F, NotifiedApprovedCVRegistrationEvent] {

  override def execute()
      : EitherT[F, DomainError, NotifiedApprovedCVRegistrationEvent] =
    EitherT.rightT[F, DomainError] {
      domain.NotifiedApprovedCVRegistrationEvent(
        validatedMailAddress,
        ApprovedCVRegistrationMessage("CV registration application approved")
      )
    }
}

// CV登録申請拒否結果を通知する
case class NotifyRejectedCVRegistrationResultCommandTrait[F[_]: Applicative](
    invalidMailAddress: InvalidMailAddress
)(implicit ec: ExecutionContext)
    extends CommandTrait[F, NotifiedRejectedCVRegistrationEvent] {

  override def execute()
      : EitherT[F, DomainError, NotifiedRejectedCVRegistrationEvent] =
    EitherT.rightT[F, DomainError] {
      domain.NotifiedRejectedCVRegistrationEvent(
        invalidMailAddress,
        RejectedCVRegistrationMessage("CV registration application rejected")
      )
    }
}
