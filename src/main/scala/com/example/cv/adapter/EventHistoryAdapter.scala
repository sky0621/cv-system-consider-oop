package com.example.cv.adapter

import com.example.cv.oop.implementation.domain.EventHistoryRepository
import slick.jdbc.JdbcBackend.Database

object EventHistoryAdapter {
  type Save[F[_]] = Database => EventHistoryRepository.Save[F]

//  def save: Save[Future] = db =>
//    (event, at) => {
//      var eh = TableQuery[EventHistories]
//      val data = eh += EventHistory(0, event, at)
//      db.run(data)
//    }
}
