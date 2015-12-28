package com.catinthedark.sszb

import com.badlogic.gdx.backends.lwjgl.{LwjglApplication, LwjglApplicationConfiguration}
import com.catinthedark.sszb.common.Const

object DesktopLauncher {
  def main(args: Array[String]) {
    val conf = new LwjglApplicationConfiguration
    conf.title = "Спаси и Сохрани Злую Бабку"
    conf.height = Const.Projection.height
    conf.width = Const.Projection.width
    conf.x = 300
    conf.y = 0

    new LwjglApplication(new SaveSoulOfZlayaBabka, conf)
    
//    TunerLauncher.start()
  }
}
