package com.catinthedark.sszb.entity

import com.catinthedark.sszb.Shared
import com.catinthedark.sszb.common.Const
import com.catinthedark.sszb.common.Const.{Difficulty, UI}

import scala.util.Random

/**
 * Created by over on 18.04.15.
 */
object Creatures {
  val rand = new Random

  def randomCreature(shared: Shared, roadNumber: Int, x: Int): Creature = {
    rand.nextInt(2) match {
      case 0 => new Whore(roadNumber, x, UI.whoreWidth, Difficulty.whoreSpeed(shared.lvl))
      case 1 => new Hooligan(roadNumber, x, UI.hooliganWidth, Const.Difficulty.hooliganSpeed(shared.lvl))
    }
  }
}

sealed trait Creature {
  var roadNumber: Int
  var x: Float
  var width: Float
  var speed: Int
  var cooldown: Boolean
}

case class Whore(var roadNumber: Int,
                 var x: Float,
                 var width: Float,
                 var speed: Int,
                 var health: Int = Const.Difficulty.whoreHealth,
                 var cooldown: Boolean = false,
                 var attacking: Boolean = false,
                 var stateTime: Float = 0f)
  extends Creature

case class Hooligan(var roadNumber: Int,
                    var x: Float,
                    var width: Float,
                    var speed: Int,
                    var health: Int = Const.Difficulty.hooliganHealth,
                    var cooldown: Boolean = false,
                    var attacking: Boolean = false,
                    var stateTime: Float = 0f)
  extends Creature
