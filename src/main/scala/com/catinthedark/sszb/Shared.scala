package com.catinthedark.sszb

import com.catinthedark.sszb.common.Const
import com.catinthedark.sszb.common.Const.Difficulty
import com.catinthedark.sszb.entity.{AnimationWrapper, Bullet, Creature, Room, Weight}
import com.catinthedark.sszb.units.RenderFactory

import scala.collection.mutable

/**
 * Created by over on 18.04.15.
 */
class Shared(var rooms: Array[Array[Room]],
             val creatures: mutable.ListBuffer[Creature],
             val weights: mutable.ListBuffer[Weight],
             val animations: mutable.ListBuffer[AnimationWrapper],
             val bullets: mutable.ListBuffer[Bullet],
             var lvl: Int,
             var lvlTime: Float,
             var hits: Int,
             var money: Int,
             var isClubBought: Boolean = false,
             var currentRoom: (Int, Int) = Difficulty.firstRoom) {
  def reset() = {
    money = Difficulty.startMoney
    lvl = 1
    hits = 0
    rooms = RenderFactory.createHouse()
    creatures.clear()
    weights.clear()
    currentRoom = Const.Difficulty.firstRoom
  }
}
