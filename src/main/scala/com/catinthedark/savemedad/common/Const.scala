package com.catinthedark.savemedad.common

import com.badlogic.gdx.math.{Rectangle, Vector2}

/**
 * Created by over on 03.01.15.
 */
object Const {

  object UI {
    var COOLDOWN_INDICATOR_COL = new Vector2(1185, 510)
    var COOLDOWN_INDICATOR_ROW = new Vector2(1185, 390)
    val gameRect = new Rectangle(345, 185, 750, 520)

    val colFistY = 770f
    val rowFistX = 160f

    val screenPos = new Vector2(320, 65)
  }

  object Physics {
    val blockSize = new Vector2(1f, 1f)
  }

  object Timing {
    var COOLDOWN_ROW_TIME = 1.5f
    val COOLDOWN_COL_TIME = 1.5f
    val fistAnimation = 0.3f
  }

  object Ints {
    var i: Int = 5
  }

  object Strings {
    var str = "Hello, World!"
  }

}
