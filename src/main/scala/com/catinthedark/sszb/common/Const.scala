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
    val clubPrice = 600000
    val potRoomCooldown = 1f
    val tvRoomCooldown = 2f
    val royalRoomCooldown = 3f
    val whoreSpeed = 150
    val hooliganSpeed = 75

    val hooliganHealth = 1
    val whoreHealth = 1

    val startMoney = 100000
    val grateMul = 80
    val repairMul = 50
    val buyMul = 100
    val royalMul = 4
    val tvMul = 2
    val potMul = 1
    val firstRoom = (1, 2)
    val weightSpeed = 250
    val bottleSpeed = 250

    val seedDivider = 10
    /**
     * seed in range [0,9]
     */
    val spawnRand: Map[Int, Function[Int, (Boolean, Boolean)]] =
      Map(
        1 -> ((seed: Int) => if (seed == 0) (true, false) else (false, false)),
        2 -> ((seed: Int) => {
          if ((0 to 1).contains(seed)) (false, true)
          else if ((1 to 2).contains(seed)) (true, false)
          else (false, false)
        }),
        3 -> ((seed: Int) => {
          if ((0 to 2).contains(seed)) (false, true)
          else if ((2 to 4).contains(seed)) (true, false)
          else (false, false)
        }),
        4 -> ((seed: Int) => {
          if ((0 to 3).contains(seed)) (true, true)
          else if ((3 to 6).contains(seed)) (true, false)
          else (false, false)
        }),
        5 -> ((seed: Int) => {
          if ((0 to 4).contains(seed)) (true, true)
          else if ((4 to 8).contains(seed)) (false, false)
          else (false, false)
        })
      )

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
