package com.catinthedark.sszb.entity

import com.catinthedark.sszb.common.Const

/**
 * Created by over on 18.04.15.
 */
sealed trait Room {
  var bought: Boolean
  var broken: Boolean
  var grate: Boolean
  var armed: Boolean
  val basePrice: Int

  def repairPrice: Int = basePrice * Const.Difficulty.repairMul
  def buyPrice: Int = basePrice * Const.Difficulty.buyMul
  def weaponPrice: Int
  def gratePrice: Int = basePrice * Const.Difficulty.grateMul
}

case class RoyalRoom(var bought: Boolean, var broken: Boolean, var grate: Boolean, var armed: Boolean, basePrice: Int)
  extends Room
{
  override def weaponPrice = basePrice * Const.Difficulty.royalMul
}

case class PotRoom(var bought: Boolean, var broken: Boolean, var grate: Boolean, var armed: Boolean, basePrice: Int)
  extends Room
{
  override def weaponPrice = basePrice * Const.Difficulty.potMul
}

case class TVRoom(var bought: Boolean, var broken: Boolean, var grate: Boolean, var armed: Boolean, basePrice: Int)
  extends Room
{
  override def weaponPrice = basePrice * Const.Difficulty.tvMul
}