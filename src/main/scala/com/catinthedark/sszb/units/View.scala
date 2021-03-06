package com.catinthedark.sszb.units

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.math.{MathUtils, Matrix4, Rectangle}
import com.catinthedark.lib.Magic._
import com.catinthedark.lib._
import com.catinthedark.sszb.Assets.{Animations, Textures}
import com.catinthedark.sszb.common.Const
import com.catinthedark.sszb.common.Const.UI
import com.catinthedark.sszb.entity._
import com.catinthedark.sszb.{Assets, Shared}

import scala.language.reflectiveCalls

/**
 * Created by over on 02.01.15.
 */
abstract class View(val shared: Shared) extends SimpleUnit with Deferred {

  var currentRoom = shared.currentRoom
  var makeSelfie = false

  val hudLayer = new Layer {

    val hudBatch = new SpriteBatch
    hudBatch.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, UI.screenSize.x, UI.screenSize.y))

    var flashAlpha = 0f
    var attackTime = 2.0f
    var stateTime = 0.0f

    override def render(delta: Float): Unit = {
      hudBatch.managed { self =>
        self.draw(Assets.Textures.hudBack, UI.hudPos().x, UI.hudPos().y)
        self.draw(Assets.Textures.hud, UI.hudPos().x, UI.hudPos().y, 80 * shared.hits, 64, 0, 0, 80 * shared.hits, 64, false, false)
        self.draw(Assets.Textures.hudFront, UI.hudPos().x, UI.hudPos().y)
        Assets.Fonts.moneyFrontFont.draw(self, "~: " + s"${shared.money}", UI.moneyPos.x, UI.moneyPos.y)
        Assets.Fonts.moneyFrontFont.draw(self, s"time:${(Const.Timing.levelTime - shared.lvlTime).toLong}", UI.timePos.x, UI.timePos.y)
        Assets.Fonts.moneyFrontFont.draw(self, s"lvl:${shared.lvl}", UI.lvlPos.x, UI.lvlPos.y)
      }

      if (makeSelfie) {
        Gdx.gl.glEnable(GL20.GL_BLEND)
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
        val shapeRenderer = new ShapeRenderer
        shapeRenderer.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, UI.screenSize.x, UI.screenSize.y))
        shapeRenderer.begin(ShapeType.Filled)
        shapeRenderer.setColor(1, 1, 1, flashAlpha)
        shapeRenderer.rect(0, 0, 1366, 768)
        shapeRenderer.end()
        Gdx.gl.glDisable(GL20.GL_BLEND)
        flashAlpha = MathUtils.cos(stateTime * (MathUtils.PI / 2) / attackTime)
        stateTime += delta
        if (stateTime >= attackTime) {
          makeSelfie = false
          stateTime = 0
        }
      }

    }

    def dispose() = {
      hudBatch.dispose()
    }
  }

  var clubKf = 0f

  val gameLayer = new Layer {

    val bgBatch = new SpriteBatch
    bgBatch.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, UI.screenSize.x, UI.screenSize.y))
    val wndBatch = new SpriteBatch
    wndBatch.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, UI.screenSize.x, UI.screenSize.y))
    val creaturesBatch = new MagicSpriteBatch(UI.drawDebug())
    creaturesBatch.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, UI.screenSize.x, UI.screenSize.y))
    var time = 0f
    val weaponsBatch = new MagicSpriteBatch(UI.drawDebug())
    weaponsBatch.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, UI.screenSize.x, UI.screenSize.y))

    override def render(delta: Float): Unit = {
      clubKf += delta
      val (x, y) = currentRoom
      time += delta

      bgBatch.managed { self =>
        self.draw(Assets.Textures.bg, 0, 0)
        self.draw(Assets.Animations.club.getKeyFrame(clubKf), Const.Physics.clubXPos, Const.Physics.clubYPos)
        if (!shared.rooms(x)(y).cooldown) {
          self.draw(Textures.babkaInWnd, y * 128 + 128, x * 128 + 256)
        }
      }
      wndBatch.managed { self =>
        for (i <- shared.rooms.indices;
             j <- shared.rooms(0).indices) {
          val room = shared.rooms(i)(j)
          if (!room.broken && room.armed && room.cooldown)
            room match {
              case r: PotRoom =>
                self.draw(Assets.Textures.wndBackPot, j * 128 + 128 + 55, i * 128 + 256 + 30)
              case r: TVRoom =>
                self.draw(Assets.Textures.wndBackTv, j * 128 + 128 + 50, i * 128 + 256 + 30)
              case r: RoyalRoom =>
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

          bgTex.foreach { tex =>
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
        def drawCreatures(c: Creature): Unit = {
          c match {
            case w: Whore =>
              val animation = if (w.attacking) {
                val a = Animations.whoreAttack
                defer(a.getAnimationDuration, () => {
                  w.attacking = false
                })
                a
              } else {
                Animations.whore
              }

              val y = if (w.roadNumber == 0) Const.UI.bottomRow() else Const.UI.topRow
              val viewPos = new Rectangle(w.x, y, w.width, 128)
              self.drawWithDebug(animation.getKeyFrame(w.stateTime), viewPos, viewPos)

              w.stateTime += delta
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

              val y = if (h.roadNumber == 0) Const.UI.bottomRow() else Const.UI.topRow
              val viewPos = new Rectangle(h.x, y, h.width, 128)
              self.drawWithDebug(animation.getKeyFrame(h.stateTime), viewPos, viewPos)

              h.stateTime += delta
            case _ =>
          }
        }

        if (shared.rooms(x)(y).cooldown) {
          val room = shared.rooms(x)(y)
          if (shared.rooms(x)(y).armed) {
            room match {
              case _: PotRoom => self.draw(Textures.pot, y * 128 + 128, x * 128 + 256 + 120)
              case _: TVRoom => self.draw(Textures.tv, y * 128 + 128 + 23, x * 128 + 256 + 115)
              case _: RoyalRoom => self.draw(Textures.royal, y * 128 + 128 - 10, x * 128 + 256 + 120)
            }
          }
          self.draw(Textures.babkaHandsUp, y * 128 + 128 + 23, x * 128 + 256 + 30)
        }

        shared.textures.foreach({ texture =>
          self.draw(texture.texture, texture.x, texture.y)
        })

        shared.creatures.filter { c =>
          c.roadNumber == 1
        }.foreach { c =>
          drawCreatures(c)
        }

        shared.creatures.filter { c =>
          c.roadNumber == 0
        }.foreach { c =>
          drawCreatures(c)
        }

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
            val viewRect = new Rectangle(tv.x, tv.y, UI.tvWidth, UI.tvHeight)
            self.drawWithDebug(Textures.tv, viewRect, viewRect)
          case pot: Pot =>
            val viewRect = new Rectangle(pot.x, pot.y, UI.potWidth, UI.potHeight)
            self.drawWithDebug(Textures.pot, viewRect, viewRect)
          case royal: Royal =>
            val viewRect = new Rectangle(royal.x, royal.y, UI.royalwWidth, UI.royalHeight)
            self.drawWithDebug(Textures.royal, viewRect, viewRect)
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
