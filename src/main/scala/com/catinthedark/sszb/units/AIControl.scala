package com.catinthedark.sszb.units

import com.catinthedark.sszb.Shared
import com.catinthedark.sszb.common.Const.Difficulty
import com.catinthedark.sszb.entity.{Room, Bottle, Hooligan}
import com.catinthedark.sszb.lib.{Deferred, SimpleUnit}

import scala.collection.mutable.ListBuffer
import scala.util.Random

abstract class AIControl(shared: Shared) extends SimpleUnit with Deferred {

  override def run(delta: Float): Unit = {
    shared.creatures.foreach {
      case h: Hooligan if h.cooldown =>
        h.cooldown = false
        h.attacking = true
        h.stateTime = 0f
        shoot(h)
        defer(2, () => h.cooldown = true)
      case c => c.x += c.speed * delta
    }
  }

  def shoot(h: Hooligan): Unit = {

    var targetRooms = new ListBuffer[Room]

    shared.rooms.foreach { roomRow =>
      roomRow.filter { room =>
        room.bought && !room.broken
      }.foreach { targetRoom =>
        targetRooms += targetRoom
      }
    }

    if(targetRooms.length > 0) {
      val random = new Random
      val targetRoom = targetRooms(random.nextInt(targetRooms.length))
      val targetRoomCenterX = targetRoom.x * 128 + 192
      val targetRoomCenterY = targetRoom.y * 128 + 320

      shared.bullets += new Bottle(h.x, h.roadNumber * 128, targetRoom, targetRoomCenterX, targetRoomCenterY, Difficulty.bottleSpeed)
    }
  }
}
