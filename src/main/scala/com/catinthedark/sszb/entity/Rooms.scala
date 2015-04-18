package com.catinthedark.sszb.entity

/**
 * Created by over on 18.04.15.
 */
abstract sealed class Room()

case class RoyalRoom(bought: Boolean, broken: Boolean, grate: Boolean, armed: Boolean, basePrice: Int) extends Room

case class PotRoom(bought: Boolean, broken: Boolean, grate: Boolean, armed: Boolean, basePrice: Int) extends Room

case class TVRoom(bought: Boolean, broken: Boolean, grate: Boolean, armed: Boolean, basePrice: Int) extends Room
