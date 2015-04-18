package com.catinthedark.tweaker

import scala.swing.Component

/**
 * Created by over on 01.02.15.
 */
trait Tweaker[T] {
  val name: String
  val category: String

  def get: T
  def set(value: T): Unit

  def component: Component
}
