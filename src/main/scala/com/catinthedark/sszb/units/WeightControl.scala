package com.catinthedark.sszb.units

import com.catinthedark.sszb.Shared
import com.catinthedark.sszb.common.Const.UI
import com.catinthedark.sszb.lib.SimpleUnit

/**
 * Created by kirill on 19.04.15.
 */
class WeightControl(shared: Shared) extends SimpleUnit {
  override def run(delta: Float): Unit = {
    shared.weights --= shared.weights.filter(_.y < UI.groundLevel)

    shared.weights.foreach { weight =>
      weight.y -= weight.speed * delta
    }
  }
}
