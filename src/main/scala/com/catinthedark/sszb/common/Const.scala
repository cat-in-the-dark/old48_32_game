package com.catinthedark.sszb.common

import com.badlogic.gdx.math.{Rectangle, Vector2}
import com.catinthedark.sszb.entity.{Whore, Hooligan, Creature}

import scala.util.Random

/**
 * Created by over on 03.01.15.
 */
object Const {

  object UI {
    val bottomRow = 16
    val topRow = 122

    val screenSize = new Vector2(1366, 768)
    var COOLDOWN_INDICATOR_COL = new Vector2(1185, 510)
    var COOLDOWN_INDICATOR_ROW = new Vector2(1185, 390)
    val gameRect = new Rectangle(345, 185, 750, 520)

    val colFistY = 770f
    val rowFistX = 160f

    val hudPos = new Vector2(961, 699)
    val moneyPos = new Vector2(1150, 685)
    val timePos = new Vector2(1150, 650)
    val lvlPos = new Vector2(1150, 615)

    val animationFastSpeed = 0.1f
    val animationSpeed = 0.2f

    val potWidth = 48f
    val tvWidth = 92f
    val royalwWidth = 128f

    val hooliganWidth = 128f
    val whoreWidth = 92f

    val hitL0Level = 192f
    val hitL1Level = 128f
    val groundLevel = 64f
  }

  object Difficulty {
    val potRoomCooldown = 1f
    val tvRoomCooldown = 2f
    val royalRoomCooldown = 3f

    val r = new Random()

    def hooliganSpeed(lvl: Int): Int = {
      65 + lvl * 10 + r.nextInt(lvl * 10)
    }

    def hooliganCooldown(lvl: Int): Float = {
      lvl match {
        case 1 => 4f
        case 2 => 4f
        case _ => (2 + r.nextInt(3)).toFloat
      }
    }

    def whoreSpeed(lvl: Int): Int = {
      130 + lvl * 10 + r.nextInt(lvl * 20)
    }

    val whoreSpeed = 150
    def whoreCooldown(lvl: Int) = {
      lvl match {
        case 1 => (4 + r.nextInt(4)).toFloat
        case 2 => (3 + r.nextInt(4)).toFloat
        case _ => (2 + r.nextInt(6)).toFloat
      }
    }

    val flashStartAlpha = 1f
    val flashSpeed = 0.3f

    val hooliganHealth = 1
    val whoreHealth = 1

    val whorePrice = 6
    val hooliganPrice = 7

    val clubBase = 20
    val cheapRoom = 1
    val normalRoom = 2
    val richRoom = 3
    val enourmoslyRichroom = 4

    val grateMul = 15
    val repairMul = 5
    val buyMul = 10
    val royalMul = 20
    val tvMul = 10
    val potMul = 5

    val startMoney = cheapRoom * buyMul
    val firstRoom = (1, 2)
    val weightSpeed = 400
    val bottleSpeed = 250

    val grateLives = 2

    val seedDivider = 10

    val clubPrice = clubBase * buyMul
    /**
     * seed in range [0,9]
     */
    def spawnRandom(lvl: Int, seed: Int): (Boolean, Boolean) = {
      lvl match {
        case 1 => if (seed < 5) (true, false) else (false, true)
        case 2 =>
          if ((0 to 4).contains(seed)) (false, true)
          else if ((5 to 9).contains(seed)) (true, false)
          else (true, true)
        case 3 =>
          if ((0 to 3).contains(seed)) (false, true)
          else if ((4 to 8).contains(seed)) (true, false)
          else (true, true)
        case 4 =>
          if ((0 to 2).contains(seed)) (false, true)
          else if ((3 to 5).contains(seed)) (true, false)
          else (true, true)
        case 5 =>
          if ((0 to 1).contains(seed)) (false, true)
          else if ((2 to 3).contains(seed)) (true, false)
          else (true, true)
        case _ => (true, true)
      }
    }

    def generatorTimer(lvl: Int): Float = {
      lvl match {
        case 1 => 2.2f
        case 2 => 2f
        case 3 => 1.8f
        case 4 => 1.8f
        case _ => 1f
      }
    }

    val creatures = Array(classOf[Hooligan], classOf[Whore])
  }


  object Physics {
    val blockSize = new Vector2(1f, 1f)
    val clubXPos = 1010
    val clubYPos = 100
  }

  object Timing {
    val levelTime = 30f
    val selfieDelay = 0.5f
  }

  object Ints {
    var i: Int = 5
  }

  object Strings {
    var str = "Hello, World!"
  }

}
