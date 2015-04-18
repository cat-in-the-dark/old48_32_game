package com.catinthedark.sszb.units


import com.badlogic.gdx.{Gdx, Input, InputAdapter}
import com.catinthedark.sszb.Shared
import com.catinthedark.sszb.common.Const
import com.catinthedark.sszb.entity.Room
import com.catinthedark.sszb.lib._

/**
 * Created by over on 22.01.15.
 */
abstract class Control(shared: Shared) extends SimpleUnit with Deferred {

  val onMoved = new Pipe[(Int, Int)]
  val onManualDay = new Pipe[Unit]
  var currentRoom = Const.Difficulty.firstRoom
  def canUseRoom(x: Int, y: Int): Boolean = shared.rooms(x)(y).bought && !shared.rooms(x)(y).broken

  def shootFrom(room: Room) = {
    println("BUM")
  }

  override def onActivate(): Unit = {
    Gdx.input.setInputProcessor(new InputAdapter {
      override def keyDown(keycode: Int): Boolean = {
        val (x, y) = currentRoom
        val rooms = shared.rooms

        keycode match {
          case Input.Keys.P =>
            shared.hits += 1
          case Input.Keys.ESCAPE =>
            onManualDay()
          case _ =>
        }

        currentRoom = keycode match {
          case Input.Keys.DOWN if x > 0 && canUseRoom(x - 1, y) => (x - 1, y)
          case Input.Keys.UP if x < rooms.length - 1 && canUseRoom(x + 1, y) => (x + 1, y)
          case Input.Keys.RIGHT if y < rooms(0).length - 1 && canUseRoom(x, y + 1) => (x, y + 1)
          case Input.Keys.LEFT if y > 0 && canUseRoom(x, y - 1) => (x, y - 1)
          case _ => (x, y)
        }
        onMoved(currentRoom)

        if (keycode == Input.Keys.SPACE) shootFrom(rooms(currentRoom._1)(currentRoom._2))
        println(currentRoom, shared.rooms(currentRoom._1)(currentRoom._2))

        true
      }

      override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
        println(s"mouse click-> (btn: $button, x: $screenX, y: $screenY")
        true
      }
    })
  }

  override def onExit(): Unit = {
    Gdx.input.setInputProcessor(null)
  }

  override def run(delta: Float) = {
    if (Gdx.input.isKeyPressed(Input.Keys.L))
      println("L pressed")
  }
}
