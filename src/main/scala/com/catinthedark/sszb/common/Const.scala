package com.catinthedark.sszb.common

import com.badlogic.gdx.math.{Rectangle, Vector2}
import com.catinthedark.sszb.entity.{Whore, Hooligan, Creature}

/**
 * Created by over on 03.01.15.
 */
object Const {

  object UI {
    val screenSize = new Vector2(1366, 768)
    var COOLDOWN_INDICATOR_COL = new Vector2(1185, 510)
    var COOLDOWN_INDICATOR_ROW = new Vector2(1185, 390)
    val gameRect = new Rectangle(345, 185, 750, 520)

    val colFistY = 770f
    val rowFistX = 160f

    val hudPos = new Vector2(961, 699)
    val moneyPos = new Vector2(1150, 655)
    val timePos = new Vector2(1150, 620)

    val animationSpeed = 0.2f

    val potWidth = 32f
    val tvWidth = 64f
    val royalwWidth = 92f

    val hitL0Level = 192f
    val hitL1Level = 128f
    val groundLevel = 64f
  }

  object Difficulty {
    val potRoomCooldown = 1f
    val tvRoomCooldown = 2f
    val royalRoomCooldown = 3f
    val whoreSpeed = 150
    val hooliganSpeed = 75

    val hooliganHealth = 1
    val whoreHealth = 1

    val whorePrice = 3
    val hooliganPrice = 4

    val clubBase = 20
    val cheapRoom = 1
    val normalRoom = 2
    val richRoom = 3

    val grateMul = 15
    val repairMul = 5
    val buyMul = 10
    val royalMul = 20
    val tvMul = 10
    val potMul = 5

    val startMoney = cheapRoom * buyMul
    val firstRoom = (1, 2)
    val weightSpeed = 250
    val bottleSpeed = 250

    val grateLives = 2

    val seedDivider = 10

    val clubPrice = clubBase * buyMul
    /**
     * seed in range [0,9]
     */
    def spawnRandom(lvl: Int, seed: Int):(Boolean, Boolean) = {
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

    def generatorTimer(lvl: Int): Float  = {
      lvl match {
        case 1 => 2.2f
        case 2 => 2f
        case 3 => 1.8f
        case 4 => 1.8f
        case _ => 1.5f
      }
    }

    val creatures = Array(classOf[Hooligan], classOf[Whore])
  }


  object Physics {
    val blockSize = new Vector2(1f, 1f)
    val clubXPos = 1100;
  }

  object Timing {
    val levelTime = 60f
  }

  object Ints {
    var i: Int = 5
  }

  object Strings {
    var str = "Hello, World!"
  }

}
