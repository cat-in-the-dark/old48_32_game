package com.catinthedark.sszb.units

import com.badlogic.gdx.math.MathUtils
import com.catinthedark.sszb.Shared
import com.catinthedark.sszb.lib.SimpleUnit

/**
 * Created by kirill on 19.04.15.
 */
class BulletControl(shared: Shared) extends SimpleUnit {
  override def run(delta: Float): Unit = {
    shared.bullets.foreach { bullet =>
      bullet.x += bullet.speed * delta * MathUtils.cos(MathUtils.atan2(bullet.targetY - bullet.y, bullet.targetX - bullet.x))
      bullet.y += bullet.speed * delta * MathUtils.sin(MathUtils.atan2(bullet.targetY - bullet.y, bullet.targetX - bullet.x))
    }
  }
}
