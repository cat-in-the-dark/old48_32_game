package com.catinthedark.sszb.units

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.catinthedark.sszb.entity.{PotRoom, TVRoom, RoyalRoom}
import com.catinthedark.sszb.{Shared, Assets}
import com.catinthedark.sszb.common.Const.UI
import com.catinthedark.sszb.lib.Magic._
import com.catinthedark.sszb.lib._

/**
 * Created by over on 02.01.15.
 */
abstract class View(val shared: Shared) extends SimpleUnit with Deferred {

  val hudLayer = new Layer {

    val hudBatch = new SpriteBatch
    val shapeRenderer = new ShapeRenderer

    override def render(delta: Float): Unit = {
      hudBatch.managed { self =>
        self.draw(Assets.Textures.hudBack, UI.hudPos.x, UI.hudPos.y)
      }

      shapeRenderer.begin(ShapeType.Filled)
      shapeRenderer.setColor(Color.BLACK)
      shapeRenderer.rect(UI.hudPos.x + shared.hits * 80, UI.hudPos.y, 400.0f - shared.hits * 80, 64.0f)
      shapeRenderer.end()

      hudBatch.managed { self =>
        self.draw(Assets.Textures.hudFront, UI.hudPos.x, UI.hudPos.y)
      }
    }
  }

  val gameLayer = new Layer {

    val bgBatch = new SpriteBatch

    override def render(delta: Float): Unit = {
      bgBatch.managed { self =>
        self.draw(Assets.Textures.bg, 0, 0)
      }
    }
  }


  override def onActivate(): Unit = {}

  override def onExit(): Unit = {}

  override def run(delta: Float) = {
    Gdx.gl.glClearColor(0, 0, 0, 0)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    gameLayer.render(delta)
    hudLayer.render(delta)
  }
}
