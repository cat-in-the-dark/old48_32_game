package com.catinthedark.sszb

import com.catinthedark.sszb.common.Const.Timing
import com.catinthedark.sszb.lib._
import com.catinthedark.sszb.units.{Control, View}

/**
 * Created by over on 14.12.14.
 */
class NightState(shared: Shared) extends YieldUnit[Boolean] {

  override def toString = "Game"
  var units: Seq[SimpleUnit] = Seq()
  var time = 0f


  var paused = false

  override def onActivate(): Unit = {
    val view = new View(shared) with LocalDeferred
    val control = new Control(shared) with LocalDeferred with Interval {
      override val interval = 0.2f
    }
    units = Seq(control, view)
    time = 0f

    units.foreach(_.onActivate())
  }

  override def onExit(): Unit = {
    units.foreach(_.onExit())
  }

  override def run(delta: Float): Option[Boolean] = {
    units.foreach(_.run(delta))
    time += delta

    if (time > Timing.levelTime)
      Some(true)
    else
      None
  }
}
