package com.example.cv.executor.api

import com.example.cv.oop.implementation.api.{
  ApiError,
  ApiFactory,
  Request,
  Response
}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
object Main {
  def main(args: Array[String]): Unit = {
    if (args.length < 1) {
      println("Usage: Main <commandName> [<parameter1, parameter2 ...]")
      sys.exit(1)
    }
    val apiName = args(0)
    val parameters = args.drop(1)

    val api = ApiFactory.create[Future, ApiError, Response](apiName)
    val maybeResponse = api.execute(Request(parameters)).value
    try {
      val eitherResponse = Await.result(maybeResponse, Duration.Inf)
      eitherResponse match {
        case Left(error)  => println(s"Error: $error")
        case Right(value) => println(s"Response: $value")
      }
    } catch {
      case ex: Exception => println(s"Exception occurred: ${ex.getMessage}")
    }
  }
}
