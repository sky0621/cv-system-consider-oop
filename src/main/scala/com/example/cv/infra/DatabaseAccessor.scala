package com.example.cv.infra

import slick.jdbc.JdbcBackend.Database

object DatabaseAccessor {
  val db = Database.forConfig("sqlite")

}
