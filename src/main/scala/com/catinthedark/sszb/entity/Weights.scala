package com.catinthedark.sszb.entity

object Weights {

}

sealed trait Weight {
  var x: Int
  var y: Int
  var speed: Int
}

case class Pot(var x: Int,
               var y: Int,
               var speed: Int)
  extends Weight

case class TV(var x: Int,
               var y: Int,
               var speed: Int)
  extends Weight

case class Royal(var x: Int,
               var y: Int,
               var speed: Int)
  extends Weight
