package com.catinthedark.sszb.entity

object Bullets {

}

sealed trait Bullet {
  var x: Float
  var y: Float
  var targetRoom: Room
  var targetX: Float
  var targetY: Float
  var speed: Int
}

case class Bottle(var x: Float,
                  var y: Float,
                  var targetRoom: Room,
                  var targetX: Float,
                  var targetY: Float,
                  var speed: Int)
  extends Bullet
