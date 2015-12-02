package com.catinthedark.sszb

import com.badlogic.gdx.{InputAdapter, Input, Gdx}
import com.catinthedark.sszb.common.Const.Timing
import com.catinthedark.sszb.common.Const.Difficulty
import com.catinthedark.sszb.entity.{Room, Creature}
import com.catinthedark.lib._
import com.catinthedark.sszb.units._

import scala.collection.mutable

/**
 * Created by over on 14.12.14.
 */
class NightState(shared: Shared) extends YieldUnit[Boolean] {

  override def toString = "Game"

  var units: Seq[SimpleUnit] = Seq()

  var paused = false
  var cheatSkip = false
  var forceReload = false

  override def onActivate(): Unit = {
    forceReload = false
    shared.hits = 0
    val view = new View(shared) with LocalDeferred
    val bulletControl = new BulletControl(shared)
    val control = new Control(shared) with LocalDeferred with Interval {
      override val interval = 0.2f
    }
    control.onMoved + (t => view.currentRoom = t)
    control.onMoved + (t => bulletControl.currentRoom = t)
    control.onManualDay + (_ => cheatSkip = true)
    control.onGameReload + (_ => forceReload = true)

    val ai = new AI(shared) with Interval with LocalDeferred {
      override val interval: Float = Difficulty.generatorTimer(shared.lvl)
    }

    val aiControl = new AIControl(shared) with LocalDeferred
    aiControl.onSelfie + (_ => view.makeSelfie = true)
    val weightsControl = new WeightControl(shared)
    val looseControl = new LooseControl(shared)

    units = Seq(control, view, ai, aiControl, bulletControl, weightsControl, looseControl)

    Assets.Audios.bgmCrickets.play()

    shared.lvlTime = 0f
    units.foreach(_.onActivate())
  }

  override def onExit(): Unit = {
    cheatSkip = false
    shared.creatures.clear()
    shared.weights.clear()
    shared.animations.clear()
    shared.textures.clear()
    shared.rooms.foreach { roomRow =>
      roomRow.foreach { room =>
        room.cooldown = true
      }
    }
    shared.bullets.clear()
    if (Assets.Audios.bgmCool.isPlaying)
      Assets.Audios.bgmCool.stop()

    if (Assets.Audios.bgmCrickets.isPlaying)
      Assets.Audios.bgmCrickets.stop()
    units.foreach(_.onExit())
  }

  override def run(delta: Float): Option[Boolean] = {
    units.foreach(_.run(delta))
    shared.lvlTime += delta

    if (cheatSkip) return Some(true)

    if (shared.lvlTime > Timing.levelTime)
      Some(true)
    else if (shared.hits >= 5) {
      Some(false)
    } else if(forceReload){
      Some(false)
    } else {
      None
    }
  }
}
