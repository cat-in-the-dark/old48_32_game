package com.catinthedark.tweaker

import java.awt.Dimension
import javax.swing.event.{ChangeEvent, ChangeListener}
import javax.swing.{JSpinner, SpinnerNumberModel, BorderFactory}

import scala.swing.{Label, FlowPanel, Component}

/**
 * Created by over on 01.02.15.
 */
trait RangeTweaker[T] extends Tweaker[T] {
  var min: T
  var max: T
  var step: T

  def modelDef: SpinnerNumberModel

  override def component: Component = {
    val _name = name

    new FlowPanel {
      contents ++= Seq(
      new Label(_name), {
        val model = modelDef
        model.addChangeListener(new ChangeListener {
          override def stateChanged(changeEvent: ChangeEvent): Unit = {
            set(model.getValue.asInstanceOf[T])
          }
        })
        Component.wrap(new JSpinner(model))
      })
    }
  }

}
