package com.example.cv

import cats.Applicative
import cats.data.EitherT
import com.example.cv.oop.implementation.domain.MailAddress.UnvalidatedMailAddress

import java.util.concurrent.atomic.AtomicInteger

object AppContext {
  type DBResult[F[_], A] = EitherT[F, String, A]
}

class InMemoryDatabase[F[_]: Applicative, A] {
  import AppContext.DBResult

  private var storage: Map[Int, A] = Map.empty
  private val counter = new AtomicInteger(0)

  def save(value: Option[A]): DBResult[F, Unit] =
    EitherT {
      Applicative[F].pure {
        value match {
          case Some(v) => {
            val id = counter.incrementAndGet()
            storage = storage + (id -> v)
            Right(())
          }
          case None => Left("No value provided to save")
        }
      }
    }

  def get(id: Int): DBResult[F, A] = EitherT {
    Applicative[F].pure {
      storage.get(id).toRight(s"Record with ID $id not found")
    }
  }
}

object InMemoryDatabase {
  def unvalidatedMailAddressStorage[F[_]: Applicative] =
    new InMemoryDatabase[F, UnvalidatedMailAddress]()
}
