package com.catinthedark.sszb.units

import com.catinthedark.sszb.{Assets, Shared}
import com.catinthedark.sszb.common.Const
import com.catinthedark.lib.SimpleUnit

/**
 * Created by over on 19.04.15.
 */
class LooseControl(shared: Shared) extends SimpleUnit {
  override def run(delta: Float): Unit = {
    val missed = shared.creatures.filter { c =>
      c.x > Const.Physics.clubXPos
    }
    shared.hits += missed.length;
    if (shared.hits > 5)
      shared.hits = 5

    shared.creatures --= missed
    if (missed.length != 0) {
      if (Assets.Audios.bgmCool.isPlaying) {
        Assets.Audios.bgmCool.pause()
      }
      Assets.Audios.bgmCool.setVolume(0.2f * shared.hits)
      Assets.Audios.bgmCool.play()
    }
  }
}
