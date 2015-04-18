package com.catinthedark.savemedad.units

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.catinthedark.savemedad.Assets
import com.catinthedark.savemedad.common.Const.UI
import com.catinthedark.savemedad.lib.Magic._
import com.catinthedark.savemedad.lib._

/**
 * Created by over on 02.01.15.
 */
abstract class View() extends SimpleUnit with Deferred {

  val gameLayer = new Layer {

    val bgBatch = new SpriteBatch
    bgBatch.getProjectionMatrix.translate(UI.screenPos.x, UI.screenPos.y, 0f)
    val entityBatch = new SpriteBatch
    entityBatch.getProjectionMatrix.translate(UI.screenPos.x, UI.screenPos.y, 0f)

    override def render(delta: Float): Unit = {
      bgBatch.managed { self =>
        self.draw(Assets.Textures.fistLeft, 0, 0)
      }
    }
  }


  override def onActivate(): Unit = {}

  override def onExit(): Unit = {}

  override def run(delta: Float) = {
    Gdx.gl.glClearColor(0, 0, 0, 0)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    gameLayer.render(delta)
  }
}
