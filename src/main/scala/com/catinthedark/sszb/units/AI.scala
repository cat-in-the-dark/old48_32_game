package com.catinthedark.sszb.units

import com.catinthedark.sszb.Shared
import com.catinthedark.sszb.common.Const.Difficulty
import com.catinthedark.sszb.entity.{Creatures, Creature, Hooligan}
import com.catinthedark.sszb.lib.{Deferred, Interval, SimpleUnit}

import scala.util.Random

/**
 * Created by over on 18.04.15.
 */
abstract class AI(shared: Shared) extends SimpleUnit with Deferred{
  val rand = new Random()

  override def run(delta: Float): Unit = {
    println("AI action!")
    val seed = rand.nextInt() % Difficulty.seedDivider
    val (needZ0, needZ1) = Difficulty.spawnRand.getOrElse(shared.lvl, (s: Int) => (true, true))(seed)
    println(needZ0, needZ1)
    if (needZ0)
      shared.creatures += Creatures.randomCreature(0, 0)
    if (needZ1)
      shared.creatures += Creatures.randomCreature(1, 0)
  }
}
