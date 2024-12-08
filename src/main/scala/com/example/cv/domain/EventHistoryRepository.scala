package com.example.cv.domain

import cats.data.EitherT

object EventHistoryRepository {
  type Save[F[_]] = (String, Long) => EitherT[F, DomainError, Unit]
}
