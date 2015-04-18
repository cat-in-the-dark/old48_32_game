package com.catinthedark.sszb.units

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.catinthedark.sszb.entity.{PotRoom, TVRoom, RoyalRoom}
import com.catinthedark.sszb.{Shared, Assets}
import com.catinthedark.sszb.common.Const.UI
import com.catinthedark.sszb.lib.Magic._
import com.catinthedark.sszb.lib._

/**
 * Created by over on 02.01.15.
 */
abstract class View(val shared: Shared) extends SimpleUnit with Deferred {

  val gameLayer = new Layer {

    val bgBatch = new SpriteBatch
    bgBatch.getProjectionMatrix.translate(UI.screenPos.x, UI.screenPos.y, 0f)
    val entityBatch = new SpriteBatch
    entityBatch.getProjectionMatrix.translate(UI.screenPos.x, UI.screenPos.y, 0f)

    override def render(delta: Float): Unit = {
      bgBatch.managed { self =>
        self.draw(Assets.Textures.fistLeft, 0, 0)
      }
//      shared.rooms.foreach(r =>
//        r match {
//          case RoyalRoom(_, broken, hasGrate, _, _) => println(s"$broken $hasGrate")
//          case TVRoom => 2
//          case PotRoom => 3
//        }
//      )
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
