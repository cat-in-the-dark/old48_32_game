package com.catinthedark.sszb

import com.catinthedark.sszb.entity.{Creature, Room}

import scala.collection.mutable

/**
 * Created by over on 18.04.15.
 */
class Shared(val rooms: Array[Array[Room]],
             val creatures: mutable.ListBuffer[Creature],
             var lvl: Int,
             var hits: Int,
             var money: Int)
