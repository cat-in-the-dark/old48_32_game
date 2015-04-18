package com.catinthedark.savemedad.lib

/**
 * Created by over on 13.12.14.
 */
trait DelayState extends Stub {
  val delay: Float
  var stateTime: Float = 0

  override def run(delta: Float) = {
    stateTime += delta
    if (stateTime > delay) Some(Unit)
    else super.run(delta)
  }

}
