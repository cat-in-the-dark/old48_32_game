package com.catinthedark.sszb.entity

/**
 * Created by over on 18.04.15.
 */
sealed abstract class Creature

case class Whore(roadNumber: Int, x: Int, health: Int) extends Creature

case class Hooligan(roadNumber: Int, x: Int, health: Int) extends Creature
