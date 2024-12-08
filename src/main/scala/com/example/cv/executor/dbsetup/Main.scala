package com.example.cv.executor.dbsetup

import com.example.cv.oop.implementation.infra.EventHistories
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

object Main extends App {
  val db = Database.forConfig("sqlite")

  private val eventHistories = TableQuery[EventHistories]

  private val setup = DBIO.seq(
    eventHistories.schema.create
  )

  try {
    Await.result(db.run(setup), 5.seconds)
  } finally {
    db.close()
  }
}
