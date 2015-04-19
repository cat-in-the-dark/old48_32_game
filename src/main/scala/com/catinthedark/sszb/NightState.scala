package com.catinthedark.sszb

import com.badlogic.gdx.{InputAdapter, Input, Gdx}
import com.catinthedark.sszb.common.Const.Timing
import com.catinthedark.sszb.common.Const.Difficulty
import com.catinthedark.sszb.entity.{Room, Creature}
import com.catinthedark.sszb.lib._
import com.catinthedark.sszb.units._

import scala.collection.mutable

/**
 * Created by over on 14.12.14.
 */
class NightState(shared: Shared) extends YieldUnit[Boolean] {

  override def toString = "Game"

  var units: Seq[SimpleUnit] = Seq()
  var time = 0f

  var paused = false
  var cheatSkip = false

  override def onActivate(): Unit = {
    shared.hits = 0
    val view = new View(shared) with LocalDeferred
    val control = new Control(shared) with LocalDeferred with Interval {
      override val interval = 0.2f
    }
    control.onMoved + (t => view.currentRoom = t)
    control.onManualDay + (_ => cheatSkip = true)

    val ai = new AI(shared) with Interval with LocalDeferred {
      override val interval: Float = 1f
    }

    val aiControl = new AIControl(shared) with LocalDeferred
    val weightsControl = new WeightControl(shared)
    val looseControl = new LooseControl(shared)

    units = Seq(control, view, ai, aiControl, weightsControl, looseControl)

    time = 0f
    units.foreach(_.onActivate())
  }

  override def onExit(): Unit = {
    cheatSkip = false
    shared.creatures.clear()
    shared.weights.clear()
    shared.rooms.foreach {roomRow =>
      roomRow.foreach {room =>
        room.cooldown = true
      }
    }
    units.foreach(_.onExit())
  }

  override def run(delta: Float): Option[Boolean] = {
    units.foreach(_.run(delta))
    time += delta

    if (cheatSkip) return Some(true)

    if (time > Timing.levelTime)
      Some(true)
    else if (shared.hits >= 5) {
      Some(false)
    } else {
      None
    }
  }
}
