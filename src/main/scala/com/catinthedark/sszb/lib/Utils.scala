package com.catinthedark.sszb.lib

import com.badlogic.gdx.Gdx

/**
 * Created by over on 15.03.15.
 */
object Utils {
  def wndYtoGL(y: Float): Float =
    Gdx.graphics.getHeight - y

}
