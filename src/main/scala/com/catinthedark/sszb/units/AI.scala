package com.catinthedark.sszb.units

import com.catinthedark.sszb.Shared
import com.catinthedark.sszb.common.Const.Difficulty
import com.catinthedark.sszb.lib.{Interval, SimpleUnit}

import scala.util.Random

/**
 * Created by over on 18.04.15.
 */
class AI(shared: Shared) extends SimpleUnit {
  val rand = new Random()

  override def run(delta: Float): Unit = {
    println("AI action!")
    val seed = rand.nextInt() % Difficulty.seedDivider
    val (needZ0, needZ1) = Difficulty.spawnRand.getOrElse(shared.lvl, (s: Int) => (true, true))(seed)
    if (needZ0)
      println("Do spawn at z0!")
    if (needZ1)
      println("Do spawn at z1!")

  }
}
