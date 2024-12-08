package com.example.cv.api

trait ApiError

case class BadRequest(cause: String) extends ApiError
case class InternalServerError(cause: String) extends ApiError
