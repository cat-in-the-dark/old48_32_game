package com.catinthedark.sszb.units


import com.badlogic.gdx.{Gdx, Input, InputAdapter}
import com.catinthedark.sszb.Shared
import com.catinthedark.sszb.lib._

/**
 * Created by over on 22.01.15.
 */
abstract class Control(shared: Shared) extends SimpleUnit with Deferred {

  override def onActivate(): Unit = {
    Gdx.input.setInputProcessor(new InputAdapter {
      override def keyDown(keycode: Int): Boolean = {
        keycode match {
          case Input.Keys.P =>
            println("key p pressed")
          case _ =>
        }
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
