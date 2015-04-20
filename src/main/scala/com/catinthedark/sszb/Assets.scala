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

    val t1 = new Texture(Gdx.files.internal("textures/menu.gif"))
    val t2 = new Texture(Gdx.files.internal("textures/t1.png"))
    val t3 = new Texture(Gdx.files.internal("textures/t2.png"))
    val gameOver = new Texture(Gdx.files.internal("textures/gameover.gif"))
    val gameWin = new Texture(Gdx.files.internal("textures/gamewin.png"))
    val hudBack = new Texture(Gdx.files.internal("textures/hud_back.png"))
    val hud = new Texture(Gdx.files.internal("textures/hud.png"))
    val hudFront = new Texture(Gdx.files.internal("textures/hud_front.png"))
    val bg = new Texture(Gdx.files.internal("textures/bg_night.gif"))
    val bgDay = new Texture(Gdx.files.internal("textures/bg_day.gif"))
    val frameTexture = TextureRegion.split(
      new Texture(Gdx.files.internal("textures/frame.png")), 138, 138)

    val wndDayNotBought = new Texture(Gdx.files.internal("textures/window_day_disabled.gif"))
    val wndDayNormal = new Texture(Gdx.files.internal("textures/window_normal_day.gif"))
    val wndDayGrate = new Texture(Gdx.files.internal("textures/window_grid_day.gif"))
    val wndDayBroken = new Texture(Gdx.files.internal("textures/window_broken_day.gif"))

    val wndNightBroken = new Texture(Gdx.files.internal("textures/window_broken_night.gif"))
    val wndNightNormal = new Texture(Gdx.files.internal("textures/window_normal_night.gif"))
    val wndNightGate = new Texture(Gdx.files.internal("textures/window_grid_night.gif"))
    val wndNightNotBought = new Texture(Gdx.files.internal("textures/window_night_disabled.gif"))

    val wndBackPot = new Texture(Gdx.files.internal("textures/flower_in_window.gif"))
    val wndBackTv = new Texture(Gdx.files.internal("textures/tv_in_window.gif"))
    val wndBackRoyal = new Texture(Gdx.files.internal("textures/piano_in_window.gif"))

    val bottle = new Texture(Gdx.files.internal("textures/bottle.png"))

    val babkaInWnd = new Texture(Gdx.files.internal("textures/babka_in_window.gif"))
    val babkaHandsUp = new Texture(Gdx.files.internal("textures/babka_hands_up.gif"))
    val whoreFrames = TextureRegion.split(
      new Texture(Gdx.files.internal("textures/whore.gif")), 88, 128)
    val hooliganAttackFrames = TextureRegion.split(
      new Texture(Gdx.files.internal("textures/gopstop.gif")), 120, 128)
    val royalCrash = TextureRegion.split(
      new Texture(Gdx.files.internal("textures/piano_crash_animation.gif")), 200, 128)
    val tvCrash = TextureRegion.split(
      new Texture(Gdx.files.internal("textures/tv_crash_animation.gif")), 160, 96)
    val potCrash = TextureRegion.split(
      new Texture(Gdx.files.internal("textures/pot_crash_animation.gif")), 80 ,104)

    val tv = new Texture(Gdx.files.internal("textures/tv.gif"))
    val royal = new Texture(Gdx.files.internal("textures/royal.gif"))
    val pot = new Texture(Gdx.files.internal("textures/pot.gif"))

    val shopBye = new Texture(Gdx.files.internal("textures/inrerface_buy.gif"))
    val shopRepair = new Texture(Gdx.files.internal("textures/inrerface_repair.gif"))
    val shopGrate = new Texture(Gdx.files.internal("textures/inrerface_grid.gif"))
    val shopWeapon = new Texture(Gdx.files.internal("textures/inrerface_weapon.gif"))
    val shopClub = new Texture(Gdx.files.internal("textures/interface_club.gif"))

    val shopWeaponPot = new Texture(Gdx.files.internal("textures/inrerface_weapon_flower.gif"))
    val shopWeaponTv = new Texture(Gdx.files.internal("textures/inrerface_weapon_tv.gif"))
    val shopWeaponRoyal = new Texture(Gdx.files.internal("textures/inrerface_weapon_piano.gif"))

    val lightOn = new Texture(Gdx.files.internal("textures/light_on.png"))
    val lightOff = new Texture(Gdx.files.internal("textures/light_off.png"))
    val lightDay = new Texture(Gdx.files.internal("textures/light_day.png"))

    val clublightFrames = TextureRegion.split(
      new Texture(Gdx.files.internal("textures/club_night_1.gif")), 382, 484)
    val clublightDay = new Texture(Gdx.files.internal("textures/club_day.gif"))

  }

  object Fonts {
    val mainGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font/main.ttf"))
    val moneyFontParam = new FreeTypeFontParameter()
    moneyFontParam.size = 44
    val moneyFrontFont = mainGenerator.generateFont(moneyFontParam)
    moneyFrontFont.setColor(167f / 255, 128f / 255, 183f / 255, 1)

    val otherFontParam = new FreeTypeFontParameter()
    otherFontParam.size = 30

    val greenFont = mainGenerator.generateFont(otherFontParam)
    greenFont.setColor(54f / 255, 131f / 255, 87f / 255, 1)
    val redFont = mainGenerator.generateFont(otherFontParam)
    redFont.setColor(255f / 255, 0f / 255, 0f / 255, 1)
  }

  object Animations {
    private def loopingAnimation(frames: Array[Array[TextureRegion]], frameIndexes: (Int, Int)*): Animation = {
      val array = new utils.Array[TextureRegion]
      frameIndexes.foreach(i => array.add(frames(i._1)(i._2)))
      new Animation(Const.UI.animationSpeed, array, Animation.PlayMode.LOOP)
    }

    private def normalAnimation(speed: Float, frames: Array[Array[TextureRegion]], frameIndexes: (Int, Int)*): Animation = {
      val array = new utils.Array[TextureRegion]
      frameIndexes.foreach(i => array.add(frames(i._1)(i._2)))
      new Animation(speed, array, Animation.PlayMode.NORMAL)
    }

    val whore = loopingAnimation(Textures.whoreFrames,
      (0, 0), (0, 1), (0, 2), (0, 3))
    val whoreAttack = loopingAnimation(Textures.whoreFrames,
      (0, 4), (0, 5), (0, 6), (0, 7))
    val hooliganAttack = normalAnimation(Const.UI.animationSpeed, Textures.hooliganAttackFrames,
      (0, 2), (0, 3), (0, 4), (0, 5), (0, 6))
    val hooligan = loopingAnimation(Textures.hooliganAttackFrames, (0, 0), (0, 1))
    val hooliganDia = normalAnimation(Const.UI.animationSpeed, Textures.hooliganAttackFrames, (0, 5), (0, 6), (0, 5), (0, 6))
    val whoreDie = normalAnimation(Const.UI.animationSpeed, Textures.whoreFrames, (0, 1), (0, 3))
    val tvCrash = normalAnimation(Const.UI.animationFastSpeed, Textures.tvCrash, (0, 0), (0, 1), (0, 2), (0, 3), (0, 4), (0, 5))
    val royalCrash = normalAnimation(Const.UI.animationFastSpeed, Textures.royalCrash, (0, 0), (0, 1), (0, 2), (0, 3), (0, 4), (0, 5))
    val potCrash = normalAnimation(Const.UI.animationFastSpeed, Textures.potCrash, (0,0), (0,1), (0,2), (0,3))
    val club = loopingAnimation(Textures.clublightFrames,
      (0, 0), (0, 1))
    club.setFrameDuration(0.6f)
    val frame = loopingAnimation(Textures.frameTexture, (0, 0), (0, 1))
    frame.setFrameDuration(0.2f)
  }

  object Audios {
    val roundEnd = Gdx.audio.newSound(Gdx.files.internal("sound/round_end.mp3"))
    val bye = Gdx.audio.newSound(Gdx.files.internal("sound/bye.mp3"))
    val potDestroy = Gdx.audio.newSound(Gdx.files.internal("sound/pot_destroy.mp3"))
    val royalDeploy = Gdx.audio.newSound(Gdx.files.internal("sound/royal_deploy.mp3"))
    val tvDestroy = Gdx.audio.newSound(Gdx.files.internal("sound/tv_destroy.mp3"))
    val wndDestroy = Gdx.audio.newSound(Gdx.files.internal("sound/window_broken.mp3"))
    val selfie = Gdx.audio.newSound(Gdx.files.internal("sound/selfie.mp3"))
    val iron = Gdx.audio.newSound(Gdx.files.internal("sound/iron.mp3"))

    val bgmCool = Gdx.audio.newMusic(Gdx.files.internal("sound/bgm_cool.mp3"))
    bgmCool.setLooping(true)

    val bgmCrickets = Gdx.audio.newMusic(Gdx.files.internal("sound/crickets.mp3"))
    bgmCrickets.setVolume(0.2f)
    bgmCrickets.setLooping(true)

    val bgmBirds = Gdx.audio.newMusic(Gdx.files.internal("sound/birds.mp3"))
    bgmBirds.setVolume(0.2f)
    bgmBirds.setLooping(true)
  }

}
