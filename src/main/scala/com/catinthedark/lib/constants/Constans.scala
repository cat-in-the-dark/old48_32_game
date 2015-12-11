package com.catinthedark.lib.constants

import com.badlogic.gdx.math.{Rectangle, Vector2}

/**
  * Created by over on 11.12.15.
  */
sealed trait Range[T] {
  val name: String
  val from: T
  val to: T
  var now: T

  def apply[T]() = now

  def set(value: T) = now = value
}
case class IRange(name: String, from: Int, to: Int, var now: Int) extends Range[Int]
case class FRange(name: String, from: Float, to: Float, var now: Float) extends Range[Float]
case class Vec2Range(name: String, from: Vector2, to: Vector2, var now: Vector2) extends Range[Vector2]
case class RectRange(name: String, from: Rectangle, to: Rectangle, var now: Rectangle) extends Range[Rectangle]

trait ConstDelegate {
  def delegate: Seq[Range[_]]

  def irange(name: String, now: Int, from: Option[Int] = None, to: Option[Int] = None) =
    new IRange(name, from.getOrElse(now * 5), to.getOrElse(-now * 5), now)

  def frange(name: String, now: Float, from: Option[Float] = None, to: Option[Float] = None) =
    new FRange(name, from.getOrElse(now * 5), to.getOrElse(-now * 5), now)

  def vec2Range(name: String, now: Vector2, from: Option[Vector2] = None, to: Option[Vector2] = None) =
    new Vec2Range(name,
      from.getOrElse(new Vector2(now).scl(5, 5)),
      to.getOrElse(new Vector2(now).scl(-5, -5)), now)

  def rectRange(name: String, now: Rectangle, from: Option[Rectangle] = None, to: Option[Rectangle] = None) =
    new RectRange(name,
      from.getOrElse(new Rectangle(now).set(now.x * 5, now.y * 5, now.width * 5, now.height * 5)),
      to.getOrElse(new Rectangle(now).set(-now.x * 5, -now.y * 5, now.width / 5, now.height / 5)), now)
}
