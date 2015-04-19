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

sealed trait Creature {
  var roadNumber: Int
  var x: Float
  var speed: Int
}

case class Whore(var roadNumber: Int,
                 var x: Float,
                 var health: Int = Const.Difficulty.whoreHealth,
                 var speed: Int = Const.Difficulty.whoreSpeed,
                 var cooldown: Boolean = true,
                 var attacking: Boolean = false,
                 var stateTime: Float = 0f)
  extends Creature

case class Hooligan(var roadNumber: Int,
                    var x: Float,
                    var health: Int = Const.Difficulty.hooliganHealth,
                    var speed: Int = Const.Difficulty.hooliganSpeed,
                    var cooldown: Boolean = true,
                    var attacking: Boolean = false,
                    var stateTime: Float = 0f)
  extends Creature
