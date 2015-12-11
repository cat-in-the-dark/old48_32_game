package com.catinthedark.sszb.units

import com.catinthedark.sszb.common.Const
import com.catinthedark.sszb.{Assets, Shared}
import com.catinthedark.sszb.common.Const.UI
import com.catinthedark.sszb.entity._
import com.catinthedark.lib.SimpleUnit

import scala.collection.mutable.ListBuffer
import scala.runtime.FloatRef
import scala.util.Random

/**
 * Created by kirill on 19.04.15.
 */
class WeightControl(shared: Shared) extends SimpleUnit {

  val r = new Random()

  def killHooligan(x: Float, y: Float) = {
    shared.animations += new AnimationWrapper(Assets.Animations.hooliganDia, x, y)
    shared.textures += new TextureWrapper(Assets.Textures.cash, x, y - 10 + r.nextInt(20))
    shared.money += Const.Difficulty.hooliganPrice
  }

  def killWhore(x: Float, y: Float) = {
    shared.animations += new AnimationWrapper(Assets.Animations.whoreDie, x, y)
    shared.textures += new TextureWrapper(Assets.Textures.cash, x, y - 10 + r.nextInt(20))
    shared.money += Const.Difficulty.whorePrice
  }

  override def run(delta: Float): Unit = {
    shared.weights --= shared.weights.filter { w =>
      if (w.y < UI.groundLevel) {
        w match {
          case p: Pot =>
            Assets.Audios.potDestroy.play()
            shared.animations += new AnimationWrapper(Assets.Animations.potCrash, p.x - 8, p.y)
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
        shared.creatures --= shared.creatures.filter { creature: Creature =>
          val wx1 = weight.x
          val wx2 = weight.x + weightWidth
          val cx1 = creature.x
          val cx2 = creature.x + creature.width
          if ((((wx1 < cx1) && ((wx2 > cx1) || (wx2 > cx2)))
            || ((wx1 >= cx1) && ((wx1 <= cx2) || (wx2 <= cx2))))
            && (creature.roadNumber == 1)) {
            creature match {
              case h: Hooligan =>
                val y = if (h.roadNumber == 0) Const.UI.bottomRow() else Const.UI.topRow
                killHooligan(h.x, y)
              case w: Whore =>
                val y = if (w.roadNumber == 0) Const.UI.bottomRow() else Const.UI.topRow
                killWhore(w.x, y)
              case _ =>
            }
            true
          } else false
        }
      }

      if (weight.y < UI.hitL1Level) {
        shared.creatures --= shared.creatures.filter { creature: Creature =>
          val wx1 = weight.x
          val wx2 = weight.x + weightWidth
          val cx1 = creature.x
          val cx2 = creature.x + creature.width
          if (((wx1 < cx1) && ((wx2 > cx1) || (wx2 > cx2)))
            || ((wx1 >= cx1) && ((wx1 <= cx2) || (wx2 <= cx2)))) {
            creature match {
              case h: Hooligan =>
                val y = if (h.roadNumber == 0) Const.UI.bottomRow() else Const.UI.topRow
                killHooligan(h.x, y)
              case w: Whore =>
                val y = if (w.roadNumber == 0) Const.UI.bottomRow() else Const.UI.topRow
                killWhore(w.x, y)
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
