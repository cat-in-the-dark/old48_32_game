package com.catinthedark.savemedad

import com.catinthedark.savemedad.lib._
import com.catinthedark.savemedad.units.{Control, View}

/**
 * Created by over on 14.12.14.
 */
class GameState extends YieldUnit[Boolean] {

  override def toString = "Game"
  val view = new View() with LocalDeferred
  val control = new Control with LocalDeferred with Interval {
    override val interval = 0.2f
  }
  val units = Seq(control, view)


  var paused = false

  override def onActivate(): Unit = {
    units.foreach(_.onActivate())
  }

  override def onExit(): Unit = {
    units.foreach(_.onExit())
  }

  override def run(delta: Float): Option[Boolean] = {
    units.foreach(_.run(delta))
    None
  }
}
