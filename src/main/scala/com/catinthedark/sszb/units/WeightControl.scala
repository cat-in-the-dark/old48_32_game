package com.catinthedark.sszb.units

import com.catinthedark.sszb.{Assets, Shared}
import com.catinthedark.sszb.common.Const.UI
import com.catinthedark.sszb.entity.{Creature, Royal, TV, Pot}
import com.catinthedark.sszb.lib.SimpleUnit

import scala.collection.mutable.ListBuffer

/**
 * Created by kirill on 19.04.15.
 */
class WeightControl(shared: Shared) extends SimpleUnit {
  override def run(delta: Float): Unit = {
    shared.weights --= shared.weights.filter { w =>
      if (w.y < UI.groundLevel) {
        w match {
          case _: Pot => Assets.Audios.potDestroy.play()
          case _: TV => Assets.Audios.tvDestroy.play()
          case _: Royal => Assets.Audios.royalDeploy.play()
        }
        true
      } else false
    }
    var deadWeightsIndex = new ListBuffer[Int]

    shared.weights.zipWithIndex.foreach { case (weight, i) =>
      var weightWidth = 0f
      var oneLevelHitTest = true
      if (weight.y < UI.hitL0Level) {
        weight match {
          case _: Pot =>
            weightWidth = UI.potWidth
            oneLevelHitTest = true
          case _: TV =>
            weightWidth = UI.tvWidth
            oneLevelHitTest = true
          case _: Royal =>
            weightWidth = UI.royalwWidth
            oneLevelHitTest = true
        }
      }

      if (weight.y < UI.hitL0Level) {
        if (oneLevelHitTest) {
          shared.creatures --= shared.creatures.filter { creature: Creature =>
            ((weight.x - weightWidth / 2) < creature.x) && (creature.x < (weight.x + weightWidth / 2) && (creature.roadNumber == 1))
          }
        }
      }

      if (weight.y < UI.hitL1Level) {
        shared.creatures --= shared.creatures.filter { creature: Creature =>
          ((weight.x - weightWidth / 2) < creature.x) && (creature.x < (weight.x + weightWidth / 2))
        }
      }
      weight.y -= weight.speed * delta
    }
  }
}
