package com.catinthedark.sszb

import java.awt.Dimension

import com.badlogic.gdx.backends.lwjgl.{LwjglApplication, LwjglApplicationConfiguration}
import com.catinthedark.sszb.common.Const
import com.catinthedark.tweaker.{DoubleTweaker, IntTweaker}

import scala.swing._

/**
 * Created by over on 13.12.14.
 */
object DesktopLauncher {
  def main(args: Array[String]) {
    val conf = new LwjglApplicationConfiguration
    conf.title = "save-me-dad-ultra"
    conf.height = 768
    conf.width = 1366

    new LwjglApplication(new SaveMeDadUltra, conf)
  }
}
