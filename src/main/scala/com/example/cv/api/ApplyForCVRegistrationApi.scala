package com.example.cv.api

import cats.data.EitherT
import cats.instances.future._
import com.example.cv.oop.implementation.domain.Birthday.UnvalidatedBirthday
import com.example.cv.oop.implementation.domain.MailAddress.UnvalidatedMailAddress
import com.example.cv.oop.implementation.domain.Name.UnvalidatedName
import com.example.cv.fp.implementation2.workflow
import com.example.cv.oop.implementation.workflow.{
  ApplyForCVRegistrationInputDto,
  ApplyForCVRegistrationOutputDto,
  ApplyForCVRegistrationWorkflow
}

import scala.concurrent.{ExecutionContext, Future}

class ApplyForCVRegistrationApi extends Api[Future, ApiError, Response] {
  override def execute(
      request: Request
  )(implicit ec: ExecutionContext): EitherT[Future, ApiError, Response] = {
    for {
      maybeValues <- ApplyForCVRegistrationApi.parse(request.values)
      inputDto = ApplyForCVRegistrationInputDto(
        maybeValues._1,
        maybeValues._2,
        maybeValues._3
      )
      outputDto <- new ApplyForCVRegistrationWorkflow()
        .execute(inputDto)
        .leftMap[ApiError](e => InternalServerError(e.message))
    } yield ApplyForCVRegistrationResponse(outputDto)
  }
}

object ApplyForCVRegistrationApi {
  private def parse(
      parameters: Array[String]
  )(implicit ec: ExecutionContext): EitherT[
    Future,
    ApiError,
    (UnvalidatedName, UnvalidatedBirthday, UnvalidatedMailAddress)
  ] = {
    EitherT.cond[Future](
      parameters.length == 6,
      (
        UnvalidatedName(parameters.head, parameters(1)),
        UnvalidatedBirthday(
          parameters(2).toInt,
          parameters(3).toInt,
          parameters(4).toInt
        ),
        UnvalidatedMailAddress(parameters(5))
      ),
      BadRequest(
        s"invalid parameter: ${parameters.mkString("Array(", ", ", ")")}"
      ): ApiError
    )
  }
}

case class ApplyForCVRegistrationResponse(
    outputDto: ApplyForCVRegistrationOutputDto
) extends Response
