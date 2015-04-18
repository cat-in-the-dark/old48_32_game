package com.catinthedark.sszb

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter

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
    val hudBack = new Texture(Gdx.files.internal("textures/hud_back.png"))
    val hud = new Texture(Gdx.files.internal("textures/hud.png"))
    val hudFront = new Texture(Gdx.files.internal("textures/hud_front.png"))
    val bg = new Texture(Gdx.files.internal("textures/bg.gif"))
    val frame = new Texture(Gdx.files.internal("textures/frame.png"))
    val wndDayNormal = new Texture(Gdx.files.internal("textures/window_normal_day.gif"))
  }

  object Fonts {
    val mainGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font/main.ttf"))
    var moneyFontParam = new FreeTypeFontParameter()
    moneyFontParam.size = 44
    var moneyBackFont = mainGenerator.generateFont(moneyFontParam)
    moneyBackFont.setColor(25f / 255, 60f / 255, 40f / 255, 1)
    var moneyFrontFont = mainGenerator.generateFont(moneyFontParam)
    moneyFrontFont.setColor(54f / 255, 131f / 255, 87f / 255, 1)
  }

}
