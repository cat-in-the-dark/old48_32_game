package com.catinthedark.sszb

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture

/**
 * Created by over on 13.12.14.
 */
object Assets {

  object Textures {
    val logo = new Texture(Gdx.files.internal("textures/logo.png"))

    val t1 = new Texture(Gdx.files.internal("textures/menu.png"))
    val t2 = new Texture(Gdx.files.internal("textures/t1.png"))
    val t3 = new Texture(Gdx.files.internal("textures/t2.png"))
    val t4 = new Texture(Gdx.files.internal("textures/t3.png"))
    val gameOver = new Texture(Gdx.files.internal("textures/gameover.png"))
    val gameWin = new Texture(Gdx.files.internal("textures/gamewin.png"))
    val fistLeft = new Texture(Gdx.files.internal("textures/fist_left.png"))
  }

}
