package com.catinthedark.sszb.lib

import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.catinthedark.sszb.common.Const.Difficulty
import com.catinthedark.sszb.units.RenderFactory
import com.catinthedark.sszb.{Shared, Assets}

class GameOverState(val shared: Shared) extends Stub("GameOver") with TextureState with KeyAwaitState {
  override val keycode: Int = Input.Keys.ENTER
  override val texture: Texture = Assets.Textures.gameOver

  override def onActivate(): Unit = {
    shared.money = Difficulty.startMoney
    shared.lvl = 1
    shared.hits = 0
    shared.rooms = RenderFactory.createHouse()
    shared.creatures.clear()
    shared.weights.clear()
    super.onActivate()
  }
}
