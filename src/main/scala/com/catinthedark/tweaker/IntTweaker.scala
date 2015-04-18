package com.catinthedark.tweaker

import javax.swing.SpinnerNumberModel

/**
 * Created by over on 01.02.15.
 */
abstract class IntTweaker(val name: String, val category: String = "") extends RangeTweaker[Int] {
  var min = Int.MinValue
  var max = Int.MaxValue
  var step = 1
  override def modelDef = new SpinnerNumberModel(get, min, max, step)
}
