package com.catinthedark.sszb.units

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.catinthedark.sszb.common.Const
import com.badlogic.gdx.math.Matrix4
import com.catinthedark.sszb.entity._
import com.catinthedark.sszb.{Shared, Assets}
import com.catinthedark.sszb.Assets.{Animations, Textures}
import com.catinthedark.sszb.common.Const.UI
import com.catinthedark.sszb.lib.Magic._
import com.catinthedark.sszb.lib._

/**
 * Created by over on 02.01.15.
 */
abstract class View(val shared: Shared) extends SimpleUnit with Deferred {

  var currentRoom = shared.currentRoom

  val hudLayer = new Layer {

    val hudBatch = new SpriteBatch
    hudBatch.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, UI.screenSize.x, UI.screenSize.y))

    override def render(delta: Float): Unit = {
      hudBatch.managed { self =>
        self.draw(Assets.Textures.hudBack, UI.hudPos.x, UI.hudPos.y)
        self.draw(Assets.Textures.hud, UI.hudPos.x, UI.hudPos.y, 80 * shared.hits, 64, 0, 0, 80 * shared.hits, 64, false, false)
        self.draw(Assets.Textures.hudFront, UI.hudPos.x, UI.hudPos.y)
        Assets.Fonts.moneyBackFont.draw(self, "~: " + s"${shared.money}", UI.moneyPos.x, UI.moneyPos.y)
        Assets.Fonts.moneyFrontFont.draw(self, "~: " + s"${shared.money}", UI.moneyPos.x + 3, UI.moneyPos.y + 3)
        Assets.Fonts.moneyFrontFont.draw(self, s"time:${(Const.Timing.levelTime - shared.lvlTime).toLong}", UI.timePos.x + 3, UI.timePos.y + 3)
      }

    }

    def dispose() = {
      hudBatch.dispose()
    }
  }

  val gameLayer = new Layer {

    val bgBatch = new SpriteBatch
    bgBatch.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, UI.screenSize.x, UI.screenSize.y))
    val wndBatch = new SpriteBatch
    wndBatch.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, UI.screenSize.x, UI.screenSize.y))
    val creaturesBatch = new SpriteBatch
    creaturesBatch.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, UI.screenSize.x, UI.screenSize.y))
    var time = 0f
    val weaponsBatch = new SpriteBatch
    weaponsBatch.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, UI.screenSize.x, UI.screenSize.y))

    override def render(delta: Float): Unit = {
      val (x, y) = currentRoom
      time += delta

      bgBatch.managed { self =>
        self.draw(Assets.Textures.bg, 0, 0)
        if (!shared.rooms(x)(y).cooldown) {
          self.draw(Textures.babkaInWnd, y * 128 + 128, x * 128 + 256)
        }
      }
      wndBatch.managed { self =>
        for (i <- 0 to shared.rooms.length - 1;
             j <- 0 to shared.rooms(0).length - 1) {
          val room = shared.rooms(i)(j)
          room match {
            case r: PotRoom if !r.broken =>
              self.draw(Assets.Textures.wndBackPot, j * 128 + 128 + 55, i * 128 + 256 + 30)
            case r: TVRoom if !r.broken =>
              self.draw(Assets.Textures.wndBackTv, j * 128 + 128 + 50, i * 128 + 256 + 30)
            case r: RoyalRoom if !r.broken =>
              self.draw(Assets.Textures.wndBackRoyal, j * 128 + 128 + 60, i * 128 + 256 + 30)
            case _ =>
          }

          val bgTex = room match {
            case r: Room if !r.broken && r.bought =>
              Some(Assets.Textures.lightOn)
            case r: Room if !r.broken && !r.bought =>
              Some(Assets.Textures.lightOff)
            case _ => None
          }

          bgTex.map { tex =>
            self.draw(tex, j * 128 + 128, i * 128 + 256)
          }

          val tex =
            if (i == x && j == y && room.cooldown) Textures.wndNightNormal
            else if (room.broken) Textures.wndNightBroken
            else if (room.grate) Textures.wndNightGate
            else if (room.bought) Textures.wndNightNormal
            else Textures.wndNightNotBought
          self.draw(tex, j * 128 + 128, i * 128 + 256)
        }
      }

      creaturesBatch.managed { self =>
        if (shared.rooms(x)(y).cooldown) {
          val room = shared.rooms(x)(y)
          room match {
            case _: PotRoom => self.draw(Textures.pot, y * 128 + 128, x * 128 + 256 + 120)
            case _: TVRoom => self.draw(Textures.tv, y * 128 + 128 + 23, x * 128 + 256 + 115)
            case _: RoyalRoom => self.draw(Textures.royal, y * 128 + 128 - 10, x * 128 + 256 + 120)
          }
          self.draw(Textures.babkaHandsUp, y * 128 + 128 + 23, x * 128 + 256 + 30)
        }
        shared.creatures.foreach({
          case w: Whore =>
            self.draw(Animations.whore.getKeyFrame(time), w.x, w.roadNumber * 128)
          case h: Hooligan =>
            val animation = if (h.attacking) {
              val a = Animations.hooliganAttack
              defer(a.getAnimationDuration, () => {
                h.attacking = false
              })
              a
            } else {
              Animations.hooligan
            }
            self.draw(animation.getKeyFrame(h.stateTime), h.x, h.roadNumber * 128)
            h.stateTime += delta
          case _ =>
        })
        shared.animations.foreach({ a =>
          self.draw(a.animation.getKeyFrame(a.stateTime), a.x, a.y)
          defer(a.animation.getAnimationDuration, () => {
            shared.animations -= a
          })
          a.stateTime += delta
        })
      }
      weaponsBatch.managed { self =>
        shared.weights.foreach {
          case tv: TV =>
            self.draw(Textures.tv, tv.x, tv.y)
          case pot: Pot =>
            self.draw(Textures.pot, pot.x, pot.y)
          case royal: Royal =>
            self.draw(Textures.royal, royal.x, royal.y)
        }
        shared.bullets.foreach { bullet =>
          self.draw(Textures.bottle, bullet.x, bullet.y)
        }
      }
    }

    def dispose() = {
      bgBatch.dispose()
      wndBatch.dispose()
    }
  }


  override def onActivate(): Unit = {
  }

  override def onExit(): Unit = {
    gameLayer.dispose()
    hudLayer.dispose()
  }

  override def run(delta: Float) = {
    Gdx.gl.glClearColor(0, 0, 0, 0)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    gameLayer.render(delta)
    hudLayer.render(delta)
  }
}
