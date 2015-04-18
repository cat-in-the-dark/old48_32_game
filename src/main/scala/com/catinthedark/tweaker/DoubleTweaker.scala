package com.catinthedark.tweaker

import javax.swing.SpinnerNumberModel

/**
 * Created by over on 01.02.15.
 */
abstract class DoubleTweaker(val name: String, val category: String = "") extends RangeTweaker[Double] {
  var min = -99999.0
  var max = 99999.0
  var step = 0.01

  override def modelDef = new SpinnerNumberModel(get, min, max, step)
}
