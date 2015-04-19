package com.catinthedark.sszb.units

import com.catinthedark.sszb.common.Const
import com.catinthedark.sszb.{Assets, Shared}
import com.catinthedark.sszb.common.Const.UI
import com.catinthedark.sszb.entity._
import com.catinthedark.sszb.lib.SimpleUnit

import scala.collection.mutable.ListBuffer
import scala.runtime.FloatRef

/**
 * Created by kirill on 19.04.15.
 */
class WeightControl(shared: Shared) extends SimpleUnit {
  def killHooligan(x: Float, y: Float) = {
    shared.animations += new AnimationWrapper(Assets.Animations.hooliganDia, x, y)
    shared.money += Const.Difficulty.hooliganPrice
  }

  def killWhore(x: Float, y: Float) = {
    shared.animations += new AnimationWrapper(Assets.Animations.whoreDie, x, y)
    shared.money += Const.Difficulty.whorePrice
  }

  override def run(delta: Float): Unit = {
    shared.weights --= shared.weights.filter { w =>
      if (w.y < UI.groundLevel) {
        w match {
          case _: Pot =>
            Assets.Audios.potDestroy.play()
          case t: TV =>
            Assets.Audios.tvDestroy.play()
            shared.animations += new AnimationWrapper(Assets.Animations.tvCrash, t.x - 34, t.y)
          case r: Royal =>
            Assets.Audios.royalDeploy.play()
            shared.animations += new AnimationWrapper(Assets.Animations.royalCrash, r.x - 30, r.y)
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
            if (((weight.x - weightWidth / 2) < creature.x) && (creature.x < (weight.x + weightWidth / 2) && (creature.roadNumber == 1))) {
              creature match {
                case h: Hooligan =>
                  val y = if (h.roadNumber == 0) Const.UI.bottomRow else Const.UI.topRow
                  killHooligan(h.x, y)
                case w: Whore =>
                  val y = if (w.roadNumber == 0) Const.UI.bottomRow else Const.UI.topRow
                  killWhore(w.x, y)
                case _ =>
              }
              true
            } else false
          }
        }
      }

      if (weight.y < UI.hitL1Level) {
        shared.creatures --= shared.creatures.filter { creature: Creature =>
           if (((weight.x - weightWidth / 2) < creature.x) && (creature.x < (weight.x + weightWidth / 2))) {
             creature match {
               case h: Hooligan =>
                 killHooligan(h.x, h.roadNumber * 128)
               case w: Whore =>
                 killWhore(w.x, w.roadNumber * 128)
               case _ =>
             }
             true
           } else false
        }
      }
      weight.y -= weight.speed * delta
    }
  }
}
