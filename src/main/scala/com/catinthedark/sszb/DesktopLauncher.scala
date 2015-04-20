package com.catinthedark.sszb

import com.badlogic.gdx.backends.lwjgl.{LwjglApplication, LwjglApplicationConfiguration}

/**
 * Created by over on 13.12.14.
 */
object DesktopLauncher {
  def main(args: Array[String]) {
    val conf = new LwjglApplicationConfiguration
    conf.title = "Спаси и Сохрани Злую Бабку"
    conf.height = 768
    conf.width = 1366

    new LwjglApplication(new SaveSoulOfZlayaBabka, conf)
  }
}
