package com.catinthedark.sszb.units

import com.badlogic.gdx.math.MathUtils
import com.catinthedark.sszb.Shared
import com.catinthedark.sszb.common.Const.Difficulty
import com.catinthedark.sszb.entity.Bullet
import com.catinthedark.sszb.lib.SimpleUnit

/**
 * Created by kirill on 19.04.15.
 */
class BulletControl(shared: Shared) extends SimpleUnit {
  var currentRoom = Difficulty.firstRoom
  
  override def run(delta: Float): Unit = {
    shared.bullets.foreach { bullet =>
      bullet.x += bullet.speed * delta * MathUtils.cos(MathUtils.atan2(bullet.targetY - bullet.y, bullet.targetX - bullet.x))
      bullet.y += bullet.speed * delta * MathUtils.sin(MathUtils.atan2(bullet.targetY - bullet.y, bullet.targetX - bullet.x))
    }
    
    shared.bullets --= shared.bullets.filter { bullet: Bullet =>
      if ((Math.abs(bullet.x - bullet.targetX) < 5) && (Math.abs(bullet.y - bullet.targetY) < 5)) {
        if ((bullet.targetRoom.x == currentRoom._2)
        && (bullet.targetRoom.y == currentRoom._1)) {
          true
        } else if (bullet.targetRoom.grate) {
          bullet.targetRoom.grateLives += -1
          if (bullet.targetRoom.grateLives <= 0) {
            bullet.targetRoom.grateLives = Difficulty.grateLives
            bullet.targetRoom.grate = false
          }
          true
        } else {
          bullet.targetRoom.broken = true
          true
        }
      } else {
        false
      }
    }
  }
}
