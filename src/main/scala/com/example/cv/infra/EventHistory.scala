package com.example.cv.infra

import slick.jdbc.SQLiteProfile.Table
import slick.jdbc.SQLiteProfile.api._
import slick.lifted.Tag

final case class EventHistory(id: Int, event: String, at: Int)

final class EventHistories(tag: Tag)
    extends Table[EventHistory](tag, "event_histories") {
  def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def event: Rep[String] = column[String]("event")
  def at: Rep[Int] = column[Int]("at")
  def * = (id, event, at) <> (EventHistory.tupled, EventHistory.unapply)
}
