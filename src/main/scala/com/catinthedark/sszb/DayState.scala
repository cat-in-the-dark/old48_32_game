package com.catinthedark.sszb

import com.badlogic.gdx.{InputAdapter, Input, Gdx}
import com.catinthedark.sszb.entity.Room
import com.catinthedark.sszb.lib.YieldUnit

/**
 * Created by over on 18.04.15.
 */
class DayState(shared: Shared) extends YieldUnit[Boolean] {
  var currentRoom = (0, 0)

  def buyOrRepair(room: Room) = {
    if (room.bought) {
      if (room.broken) {
        room.broken = false
        println("Pay money for repair!")
      }
    } else {
      room.bought = true
      println("Pay money for new room!")
    }
  }

  def buyGate(room: Room) = {
    if (!room.grate) {
      room.grate = true
      println("Pay money for grate")
    }
  }

  def buyWeapon(room: Room) = {
    if (!room.armed) {
      room.armed = true
      println("Pay money for weapon")
    }
  }

  override def toString = "Day"

  override def onActivate(): Unit = {
    Gdx.input.setInputProcessor(new InputAdapter {
      override def keyDown(keycode: Int): Boolean = {
        val (x, y) = currentRoom
        val rooms = shared.rooms
        currentRoom = keycode match {
          case Input.Keys.UP if x > 0 => (x - 1, y)
          case Input.Keys.DOWN if x < rooms.length - 1 => (x + 1, y)
          case Input.Keys.RIGHT if y < rooms(0).length - 1 => (x, y + 1)
          case Input.Keys.LEFT if y > 0 => (x, y - 1)
          case _ => (x, y)
        }

        val room = rooms(currentRoom._1)(currentRoom._2)
        keycode match {
          case Input.Keys.NUM_1 => buyOrRepair(room)
          case Input.Keys.NUM_2 if room.bought && !room.broken => buyGate(room)
          case Input.Keys.NUM_3 if room.bought && !room.broken => buyWeapon(room)
          case _ =>
        }

        println(currentRoom, shared.rooms(currentRoom._1)(currentRoom._2))
        true
      }
    })
  }

  override def onExit(): Unit = {

  }

  override def run(delta: Float): Option[Boolean] = {
    if (Gdx.input.isKeyPressed(Input.Keys.ENTER))
      Some(false)
    else
      None
  }
}
