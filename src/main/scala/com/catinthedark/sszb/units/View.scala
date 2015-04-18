package com.catinthedark.sszb.units

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.catinthedark.sszb.common.Const
import com.badlogic.gdx.math.Matrix4
import com.catinthedark.sszb.entity.{PotRoom, TVRoom, RoyalRoom}
import com.catinthedark.sszb.{Shared, Assets}
import com.catinthedark.sszb.Assets.Textures
import com.catinthedark.sszb.common.Const.UI
import com.catinthedark.sszb.lib.Magic._
import com.catinthedark.sszb.lib._

/**
 * Created by over on 02.01.15.
 */
abstract class View(val shared: Shared) extends SimpleUnit with Deferred {

  var currentRoom = Const.Difficulty.firstRoom

  val hudLayer = new Layer {

    val hudBatch = new SpriteBatch
    hudBatch.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, UI.screenSize.x, UI.screenSize.y))

    override def render(delta: Float): Unit = {
      hudBatch.managed { self =>
        self.draw(Assets.Textures.hudBack, UI.hudPos.x, UI.hudPos.y)
        self.draw(Assets.Textures.hud, UI.hudPos.x, UI.hudPos.y, 80 * shared.hits, 64, 0, 0, 80 * shared.hits, 64, false, false)
        self.draw(Assets.Textures.hudFront, UI.hudPos.x, UI.hudPos.y)
        Assets.Fonts.moneyBackFont.draw(self, "$: " + s"${shared.money}", UI.moneyPos.x, UI.moneyPos.y)
        Assets.Fonts.moneyFrontFont.draw(self, "$: " + s"${shared.money}", UI.moneyPos.x + 3, UI.moneyPos.y + 3)
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

    override def render(delta: Float): Unit = {
      bgBatch.managed { self =>
        self.draw(Assets.Textures.bg, 0, 0)
      }
      wndBatch.managed { self =>
        for (i <- 0 to shared.rooms.length - 1;
             j <- 0 to shared.rooms(0).length - 1) {
          val room = shared.rooms(i)(j)
          val tex = if (room.broken) Textures.wndNightBroken
          else if (room.grate) Textures.wndNightGate
          else if (room.bought) Textures.wndNightNormal
          else Textures.wndNightNotBought
          self.draw(tex, j * 128 + 128, i * 128 + 256)
        }
      }
    }
    def dispose() = {
      bgBatch.dispose()
      wndBatch.dispose()
    }
  }


  override def onActivate(): Unit = {}

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
