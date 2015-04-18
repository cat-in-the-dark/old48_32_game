package com.catinthedark.sszb.units

import com.catinthedark.sszb.common.Const
import com.catinthedark.sszb.entity.{RoyalRoom, TVRoom, PotRoom, Room}


/**
 * Created by over on 02.01.15.
 */
object RenderFactory {
  def createHouse(): Array[Array[Room]] = {
    val lvl = Array(
      Array((1, 100), (1, 100), (1, 100), (1, 100), (1, 100), (1, 100)),
      Array((2, 200), (2, 200), (2, 200), (2, 200), (2, 200), (2, 200)),
      Array((3, 300), (3, 300), (3, 300), (3, 300), (3, 300), (3, 300))
    )
    val rooms = lvl.map(row => {
      row.map(roomType => {
        val room = roomType match {
          case (1, price) => PotRoom(false, false, false, false, price)
          case (2, price) => TVRoom(false, false, false, false, price)
          case (3, price) => RoyalRoom(false, false, false, false, price)
        }
        room.asInstanceOf[Room]
      })
    })
    val (x, y) = Const.Difficulty.firstRoom
    rooms(x)(y).bought = true
    rooms(x)(y).armed = true
    rooms(x+1)(y).bought = true
    rooms(x+1)(y).armed = true

    rooms
  }
}
