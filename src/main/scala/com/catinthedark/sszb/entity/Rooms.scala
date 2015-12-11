package com.catinthedark.sszb.entity

import com.catinthedark.sszb.common.Const
import com.catinthedark.sszb.common.Const.Difficulty

/**
 * Created by over on 18.04.15.
 */
sealed trait Room {
  var bought: Boolean
  var broken: Boolean
  var grate: Boolean
  var armed: Boolean
  var cooldown: Boolean
  val basePrice: Int
  val x: Int
  val y: Int
  var grateLives: Int

  def cooldownTime: Float

  def repairPrice: Int = basePrice * Const.Difficulty.repairMul

  def buyPrice: Int = basePrice * Const.Difficulty.buyMul

  def weaponPrice: Int

  def gratePrice: Int = basePrice * Const.Difficulty.grateMul
}

case class RoyalRoom(var bought: Boolean, var broken: Boolean, var grate: Boolean, var armed: Boolean, var cooldown: Boolean, basePrice: Int, x: Int, y: Int, var grateLives: Int = Difficulty.grateLives)
  extends Room {
  override def weaponPrice = basePrice * Const.Difficulty.royalMul

  override def cooldownTime: Float = Const.Difficulty.royalRoomCooldown
}

case class PotRoom(var bought: Boolean, var broken: Boolean, var grate: Boolean, var armed: Boolean, var cooldown: Boolean, basePrice: Int, x: Int, y: Int, var grateLives: Int = Difficulty.grateLives)
  extends Room {
  override def weaponPrice = basePrice * Const.Difficulty.potMul

  override def cooldownTime: Float = Const.Difficulty.potRoomCooldown()
}

case class TVRoom(var bought: Boolean, var broken: Boolean, var grate: Boolean, var armed: Boolean, var cooldown: Boolean, basePrice: Int, x: Int, y: Int, var grateLives: Int = Difficulty.grateLives)
  extends Room {
  override def weaponPrice = basePrice * Const.Difficulty.tvMul

  override def cooldownTime: Float = Const.Difficulty.tvRoomCooldown
}
