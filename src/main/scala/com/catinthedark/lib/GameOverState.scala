package com.catinthedark.lib

import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.catinthedark.sszb.{Assets, Shared}

class GameOverState(val shared: Shared) extends Stub("GameOver") with TextureState with KeyAwaitState {
  override val keycode: Int = Input.Keys.ENTER
  override val texture: Texture = Assets.Textures.gameOver

  override def onActivate(): Unit = {
    shared.reset()
    super.onActivate()
  }
}
