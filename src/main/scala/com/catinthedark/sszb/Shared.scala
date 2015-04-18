package com.catinthedark.sszb

import com.catinthedark.sszb.entity.{Weight, Creature, Room}

import scala.collection.mutable

/**
 * Created by over on 18.04.15.
 */
class Shared(var rooms: Array[Array[Room]],
             val creatures: mutable.ListBuffer[Creature],
             val weights: mutable.ListBuffer[Weight],
             var lvl: Int,
             var hits: Int,
             var money: Int)
