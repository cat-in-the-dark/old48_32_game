package com.catinthedark.sszb

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.layout.VBox
import javafx.stage.Stage

import com.catinthedark.lib.constants._
import com.catinthedark.sszb.common.Const

class ApplicationTuner extends Application {

  override def start(stage: Stage): Unit = {
    val root: VBox = FXMLLoader.load(getClass.getClassLoader.getResource("layout.fxml"))
    Const.delegate.foreach { d =>
      val el = d match {
        case onOff: OnOff =>
          new OnOffControl(onOff, FXMLLoader.load(getClass.getClassLoader.getResource("onOff.fxml"))).el
        case range: IRange =>
          new IRangeControl(range, FXMLLoader.load(getClass.getClassLoader.getResource("range.fxml"))).el
        case range: FRange =>
          new FRangeControl(range, FXMLLoader.load(getClass.getClassLoader.getResource("range.fxml"))).el
        case range: Vec2Range =>
          new Vec2RangeControl(range, FXMLLoader.load(getClass.getClassLoader.getResource("vec2Range.fxml"))).el
      }
      root.getChildren.add(el)
    }
    val sc = new Scene(root, 275, 500)

    stage.setTitle("Property Tuner v0.0.0")
    stage.setX(0)
    stage.setY(0)
    stage.setScene(sc)
    stage.show()
    stage.getScene.getWindow.setX(0)
  }
}

object TunerLauncher {
  def start(): Unit = {
    new Thread(new Runnable {
      override def run(): Unit = {
        Application.launch(classOf[ApplicationTuner])
      }
    }).start()
  }
}