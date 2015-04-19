package com.catinthedark.sszb.units

import com.catinthedark.sszb.Shared
import com.catinthedark.sszb.common.Const
import com.catinthedark.sszb.lib.SimpleUnit

/**
 * Created by over on 19.04.15.
 */
class LooseControl(shared: Shared) extends SimpleUnit {
  override def run(delta: Float): Unit = {
    val nCreature = shared.creatures.length
    val missed = shared.creatures.filter { c =>
      c.x > Const.Physics.clubXPos
    }
    shared.hits += missed.length;
    if (shared.hits > 5)
      shared.hits = 5

    shared.creatures --= missed
  }
}
