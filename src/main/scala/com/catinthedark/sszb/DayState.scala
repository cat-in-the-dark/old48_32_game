package com.catinthedark.sszb

import com.badlogic.gdx.{Input, Gdx}
import com.catinthedark.sszb.lib.YieldUnit

/**
 * Created by over on 18.04.15.
 */
class DayState(shared: Shared) extends YieldUnit[Boolean] {
  override def toString = "Day"
  override def onActivate(): Unit = {

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
