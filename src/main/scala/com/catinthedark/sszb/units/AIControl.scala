package com.catinthedark.sszb.units

import com.catinthedark.sszb.Shared
import com.catinthedark.sszb.entity.Hooligan
import com.catinthedark.sszb.lib.{Deferred, SimpleUnit}

abstract class AIControl(shared: Shared) extends SimpleUnit with Deferred {

  override def run(delta: Float): Unit = {
    shared.creatures.foreach { c =>
      c.x += c.speed
      c match {
        case h @ Hooligan(_,_,_,_,_) if h.cooldown =>
          h.cooldown = false
          println(h, "Shoot!!!")
          defer(2, () => h.cooldown = true)
        case _ =>
      }
    }
  }
}
