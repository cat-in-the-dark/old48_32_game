package com.catinthedark.sszb.common

import com.badlogic.gdx.math.{Rectangle, Vector2}

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
  }

  object Difficulty {
    val startMoney: Int = 100000
    val grateMul = 80
    val repairMul = 50
    val buyMul = 100
    val royalMul = 4
    val tvMul = 2
    val potMul = 1
    val firstRoom = (1, 2)

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
  }


  object Physics {
    val blockSize = new Vector2(1f, 1f)
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
