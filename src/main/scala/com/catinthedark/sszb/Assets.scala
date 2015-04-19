package com.catinthedark.sszb

import com.badlogic.gdx.{utils, Gdx}
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{Animation, TextureRegion}
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter
import com.catinthedark.sszb.common.Const

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

    val wndDayNotBought = new Texture(Gdx.files.internal("textures/window_day_disabled.gif"))
    val wndDayNormal = new Texture(Gdx.files.internal("textures/window_normal_day.gif"))
    val wndDayGrate = new Texture(Gdx.files.internal("textures/window_grid_day.gif"))
    val wndDayBroken = new Texture(Gdx.files.internal("textures/window_broken_day.gif"))

    val wndNightBroken = new Texture(Gdx.files.internal("textures/window_broken_night.gif"))
    val wndNightNormal = new Texture(Gdx.files.internal("textures/window_normal_night.gif"))
    val wndNightGate = new Texture(Gdx.files.internal("textures/window_grid_night.gif"))
    val wndNightNotBought = new Texture(Gdx.files.internal("textures/window_night_disabled.gif"))

    val babkaInWnd = new Texture(Gdx.files.internal("textures/babka_in_window.gif"))
    val whoreFrames = TextureRegion.split(
      new Texture(Gdx.files.internal("textures/whore.png")), 92, 128)
    val hooliganAttackFrames = TextureRegion.split(
      new Texture(Gdx.files.internal("textures/hooligan.png")), 120, 128)

    val tv = new Texture(Gdx.files.internal("textures/tv.png"))
    val royal = new Texture(Gdx.files.internal("textures/royal.png"))
    val pot = new Texture(Gdx.files.internal("textures/pot.png"))

    val shopBye = new Texture(Gdx.files.internal("textures/inrerface_buy.gif"))
    val shopRepair = new Texture(Gdx.files.internal("textures/inrerface_repair.gif"))
    val shopGrate = new Texture(Gdx.files.internal("textures/inrerface_grid.gif"))
    val shopWeapon = new Texture(Gdx.files.internal("textures/inrerface_weapon.gif"))
    val shopClub = new Texture(Gdx.files.internal("textures/interface_club.gif"))
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

  object Animations {
    private def loopingAnimation(frames: Array[Array[TextureRegion]], frameIndexes: (Int, Int)*): Animation = {
      val array = new utils.Array[TextureRegion]
      frameIndexes.foreach(i => array.add(frames(i._1)(i._2)))
      new Animation(Const.UI.animationSpeed, array, Animation.PlayMode.LOOP)
    }

    private def normalAnimation(frames: Array[Array[TextureRegion]], frameIndexes: (Int, Int)*): Animation = {
      val array = new utils.Array[TextureRegion]
      frameIndexes.foreach(i => array.add(frames(i._1)(i._2)))
      new Animation(Const.UI.animationSpeed, array, Animation.PlayMode.NORMAL)
    }

    val whore = loopingAnimation(Textures.whoreFrames,
      (0, 0), (0, 1), (0, 2), (0, 3))
    val hooliganAttack = normalAnimation(Textures.hooliganAttackFrames,
      (0,0), (0,1),(0,2),(0,3),(0,4),(0,5),(0,6),(0,7),(0,8),(0,9))
    val hooligan = loopingAnimation(Textures.hooliganAttackFrames, (0,0), (0,1))
    val hooliganDia = normalAnimation(Textures.hooliganAttackFrames, (0,8), (0,9), (0,8), (0,9))
    val whoreDie = normalAnimation(Textures.whoreFrames, (0, 1), (0, 3))
  }

  object Audios {
    val roundEnd = Gdx.audio.newSound(Gdx.files.internal("sound/round_end.mp3"))
    val bye = Gdx.audio.newSound(Gdx.files.internal("sound/bye.mp3"))
    val potDestroy = Gdx.audio.newSound(Gdx.files.internal("sound/pot_destroy.mp3"))
    val royalDeploy = Gdx.audio.newSound(Gdx.files.internal("sound/royal_deploy.mp3"))
    val tvDestroy = Gdx.audio.newSound(Gdx.files.internal("sound/tv_destroy.mp3"))
  }

}
