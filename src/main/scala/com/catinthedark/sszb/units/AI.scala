package com.catinthedark.sszb.units

import com.catinthedark.sszb.Shared
import com.catinthedark.sszb.common.Const.Difficulty
import com.catinthedark.sszb.entity.{Whore, Creatures, Creature, Hooligan}
import com.catinthedark.lib.{Deferred, Interval, SimpleUnit}

import scala.util.Random

/**
 * Created by over on 18.04.15.
 */
abstract class AI(shared: Shared) extends SimpleUnit with Deferred{
  val rand = new Random()

  override def run(delta: Float): Unit = {
    val seed = rand.nextInt() % Difficulty.seedDivider
    val (needZ0, needZ1) = Difficulty.spawnRandom(shared.lvl, seed)
    if (needZ0) {
      var c = Creatures.randomCreature(shared, 0, 0)
      c match {
        case h: Hooligan => defer(Difficulty.hooliganCooldown(shared.lvl) / 2, () => h.cooldown = true)
        case w: Whore => defer(Difficulty.whoreCooldown(shared.lvl), () => w.cooldown = true)
      }
      shared.creatures += c
    }
    if (needZ1) {
      var c = Creatures.randomCreature(shared, 1, 0)
      c match {
        case h: Hooligan => defer(Difficulty.hooliganCooldown(shared.lvl) / 2, () => h.cooldown = true)
        case w: Whore => defer(Difficulty.whoreCooldown(shared.lvl), () => w.cooldown = true)
      }
      shared.creatures += c
    }
  }
}
