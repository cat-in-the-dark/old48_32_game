package com.catinthedark.sszb.units

import com.catinthedark.sszb.common.Const.Difficulty
import com.catinthedark.sszb.entity.{RoyalRoom, TVRoom, PotRoom, Room}


/**
 * Created by over on 02.01.15.
 */
object RenderFactory {
  def createHouse(): Array[Array[Room]] = {
    val lvl = Array(
      Array((2, Difficulty.richRoom), (1, Difficulty.cheapRoom), (1, Difficulty.cheapRoom), (1, Difficulty.cheapRoom), (2, Difficulty.normalRoom), (3, Difficulty.richRoom)),
      Array((1, Difficulty.cheapRoom), (1, Difficulty.normalRoom), (2, Difficulty.normalRoom), (2, Difficulty.normalRoom), (1, Difficulty.normalRoom), (3, Difficulty.richRoom)),
      Array((3, Difficulty.normalRoom), (3, Difficulty.richRoom), (1, Difficulty.cheapRoom), (3, Difficulty.normalRoom), (2, Difficulty.richRoom), (1, Difficulty.cheapRoom))
    )
    val rooms = (for {i <- 0 to lvl.length - 1
                      line = (for {
                        j <- 0 to lvl(0).length - 1
                        room = (lvl(i)(j) match {
                          case (1, price) => PotRoom(false, false, false, false, true, price, j, i)
                          case (2, price) => TVRoom(false, false, false, false, true, price, j, i)
                          case (3, price) => RoyalRoom(false, false, false, false, true, price, j, i)
                        }).asInstanceOf[Room]
                      } yield room).toArray
    } yield line).toArray

    val (x, y) = Difficulty.firstRoom
    rooms(x)(y).bought = true
    rooms(x)(y).armed = true
    rooms(x + 1)(y).bought = true
    rooms(x + 1)(y).armed = true
    rooms(x + 1)(y).broken = false

    rooms
  }

}
