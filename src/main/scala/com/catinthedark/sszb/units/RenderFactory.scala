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

    val (x, y) = Const.Difficulty.firstRoom
    rooms(x)(y).bought = true
    rooms(x)(y).armed = true
    rooms(x + 1)(y).bought = true
    rooms(x + 1)(y).armed = true
    rooms(x + 1)(y).broken = true

    rooms
  }

}
