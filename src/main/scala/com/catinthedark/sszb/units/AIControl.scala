package com.catinthedark.sszb.units

import com.catinthedark.sszb.Shared
import com.catinthedark.sszb.entity.Hooligan
import com.catinthedark.sszb.lib.{Deferred, SimpleUnit}

abstract class AIControl(shared: Shared) extends SimpleUnit with Deferred {

  override def run(delta: Float): Unit = {
    shared.creatures.foreach {
      case h: Hooligan if h.cooldown =>
        h.cooldown = false
        h.attacking = true
        h.stateTime = 0f
        println(h, "Shoot!!!")
        defer(2, () => h.cooldown = true)
      case c => c.x += c.speed
    }
  }
}
