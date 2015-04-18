package com.catinthedark.sszb.entity

/**
 * Created by over on 18.04.15.
 */
sealed trait Room {
  var bought: Boolean
  var broken: Boolean
  var grate: Boolean
  var armed: Boolean
  val basePrice: Int
}

case class RoyalRoom(var bought: Boolean, var broken: Boolean, var grate: Boolean, var armed: Boolean, basePrice: Int)
  extends Room

case class PotRoom(var bought: Boolean, var broken: Boolean, var grate: Boolean, var armed: Boolean, basePrice: Int)
  extends Room

case class TVRoom(var bought: Boolean, var broken: Boolean, var grate: Boolean, var armed: Boolean, basePrice: Int)
  extends Room
