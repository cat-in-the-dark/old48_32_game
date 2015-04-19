package com.catinthedark.sszb.entity

object Weights {

}

sealed trait Weight {
  var x: Float
  var y: Float
  var speed: Int
}

case class Pot(var x: Float,
               var y: Float,
               var speed: Int)
  extends Weight

case class TV(var x: Float,
               var y: Float,
               var speed: Int)
  extends Weight

case class Royal(var x: Float,
               var y: Float,
               var speed: Int)
  extends Weight
