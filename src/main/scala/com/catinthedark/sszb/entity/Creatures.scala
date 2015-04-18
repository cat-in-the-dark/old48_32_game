package com.catinthedark.sszb.entity

import com.catinthedark.sszb.common.Const

import scala.util.Random

/**
 * Created by over on 18.04.15.
 */
object Creatures {
  val rand = new Random
  def randomCreature(roadNumber: Int, x: Int): Creature = {
    rand.nextInt(2) match {
      case 0 => new Whore(roadNumber, x)
      case 1 => new Hooligan(roadNumber, x)
    }
  }
}

sealed abstract class Creature

case class Whore(roadNumber: Int, x: Int, health: Int = Const.Difficulty.whoreHealth) extends Creature

case class Hooligan(roadNumber: Int, x: Int, health: Int = Const.Difficulty.hooliganHealth) extends Creature
