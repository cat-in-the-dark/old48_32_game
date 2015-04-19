package com.catinthedark.sszb.entity

import com.catinthedark.sszb.common.Const
import com.catinthedark.sszb.common.Const.UI

import scala.util.Random

/**
 * Created by over on 18.04.15.
 */
object Creatures {
  val rand = new Random

  def randomCreature(roadNumber: Int, x: Int): Creature = {
    rand.nextInt(2) match {
      case 0 => new Whore(roadNumber, x, UI.whoreWidth)
      case 1 => new Hooligan(roadNumber, x, UI.hooliganWidth)
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
                 var health: Int = Const.Difficulty.whoreHealth,
                 var speed: Int = Const.Difficulty.whoreSpeed,
                 var cooldown: Boolean = false,
                 var attacking: Boolean = false,
                 var stateTime: Float = 0f)
  extends Creature

case class Hooligan(var roadNumber: Int,
                    var x: Float,
                    var width: Float,
                    var health: Int = Const.Difficulty.hooliganHealth,
                    var speed: Int = Const.Difficulty.hooliganSpeed,
                    var cooldown: Boolean = false,
                    var attacking: Boolean = false,
                    var stateTime: Float = 0f)
  extends Creature
