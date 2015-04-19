package com.catinthedark.sszb

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.{InputAdapter, Input, Gdx}
import com.catinthedark.sszb.common.Const
import com.catinthedark.sszb.common.Const.UI
import com.catinthedark.sszb.entity.{RoyalRoom, TVRoom, PotRoom, Room}
import com.catinthedark.sszb.lib.YieldUnit
import com.catinthedark.sszb.lib.Magic._
import Assets.Textures

/**
 * Created by over on 18.04.15.
 */
class DayState(shared: Shared) extends YieldUnit[Boolean] {
  var currentRoom = Const.Difficulty.firstRoom

  def repair(room: Room) = {
    if (shared.money >= room.repairPrice) {
      room.broken = false
      Assets.Audios.bye.play()
      shared.money -= room.repairPrice
    } else {
      println("You have no money for repairing!")
    }
  }

  def buy(room: Room) = {
    if (shared.money >= room.buyPrice) {
      room.bought = true
      Assets.Audios.bye.play()
      shared.money -= room.buyPrice
    } else {
      println("You have no money for buying new room!")
    }
  }

  def buyOrRepair(room: Room) = {
    if (room.bought) {
      if (room.broken) repair(room)
    } else {
      buy(room)
    }
  }

  def buyGate(room: Room) = {
    if (!room.grate) {
      if (shared.money >= room.gratePrice) {
        room.grate = true
        Assets.Audios.bye.play()
        shared.money -= room.gratePrice
      } else {
        println("You have no money for installing grate")
      }
    }
  }

  def buyWeapon(room: Room) = {
    if (!room.armed) {
      if (shared.money >= room.weaponPrice) {
        room.armed = true
        Assets.Audios.bye.play()
        shared.money -= room.weaponPrice
      } else {
        println("You have no money for installing weapon")
      }
    }
  }

  def query(i: Int, j: Int): Boolean =
    try {
      shared.rooms(i)(j).bought
    } catch {
      case e: ArrayIndexOutOfBoundsException => false
    }

  def canUseRoom(x: Int, y: Int): Boolean =
    shared.rooms(x)(y).bought || query(x + 1, y) || query(x - 1, y) || query(x, y + 1) || query(x, y - 1)

  override def toString = "Day"

  override def onActivate(): Unit = {
    Assets.Audios.roundEnd.play()
    Gdx.input.setInputProcessor(new InputAdapter {
      override def keyDown(keycode: Int): Boolean = {
        val (x, y) = currentRoom
        val rooms = shared.rooms
        currentRoom = keycode match {
          case Input.Keys.DOWN if x > 0 && canUseRoom(x - 1, y) => (x - 1, y)
          case Input.Keys.UP if x < rooms.length - 1 && canUseRoom(x + 1, y) => (x + 1, y)
          case Input.Keys.RIGHT if y < rooms(0).length - 1 && canUseRoom(x, y + 1) => (x, y + 1)
          case Input.Keys.LEFT if y > 0 && canUseRoom(x, y - 1) => (x, y - 1)
          case _ => (x, y)
        }

        val room = rooms(currentRoom._1)(currentRoom._2)
        keycode match {
          case Input.Keys.NUM_1 => buyOrRepair(room)
          case Input.Keys.NUM_2 if room.bought && !room.broken => buyGate(room)
          case Input.Keys.NUM_3 if room.bought && !room.broken => buyWeapon(room)
          case _ =>
        }

        println(shared.money, currentRoom, shared.rooms(currentRoom._1)(currentRoom._2))
        true
      }
    })
  }

  val batch = new SpriteBatch

  override def onExit(): Unit = {

  }

  def render(): Unit = {
    batch.managed { self =>
      self.draw(Textures.bg, 0, 0)
      for (i <- 0 to shared.rooms.length - 1;
           j <- 0 to shared.rooms(0).length - 1) {

        val room = shared.rooms(i)(j)
        val tex = if (room.broken) Textures.wndDayBroken
        else if (room.grate) Textures.wndDayGrate
        else if (room.bought) Textures.wndDayNormal
        else Textures.wndDayNotBought

        self.draw(tex, j * 128 + 128, i * 128 + 256)

        Assets.Fonts.moneyBackFont.draw(self, "~: " + s"${shared.money}", UI.moneyPos.x, UI.moneyPos.y)
        Assets.Fonts.moneyFrontFont.draw(self, "~: " + s"${shared.money}", UI.moneyPos.x + 3, UI.moneyPos.y + 3)
      }
      //Bye/Repair
      val (x, y) = currentRoom
      self.draw(Textures.frame, y * 128 + 128, x * 128 + 256)
      val room = shared.rooms(x)(y)
      if (!room.bought) {
        self.draw(Textures.shopBye, 0, 0)
        val font =
          if (shared.money > room.buyPrice)
            Assets.Fonts.greenFont
          else
            Assets.Fonts.redFont
        font.draw(self, s"~${room.buyPrice}", 200, 70)
      }
      else {
        self.draw(Textures.shopRepair, 0, 0)
        if (room.broken) {
          val font =
            if (shared.money >= room.repairPrice)
              Assets.Fonts.greenFont
            else
              Assets.Fonts.redFont
          font.draw(self, s"~${room.repairPrice}", 200, 70)
        }
      }

      //Grate
      self.draw(Textures.shopGrate, 343, 0)
      if (!room.grate && !room.broken) {
        val font =
          if (shared.money >= room.gratePrice)
            Assets.Fonts.greenFont
          else
            Assets.Fonts.redFont
        font.draw(self, s"~${room.gratePrice}", 543, 70)
      }
      //Weapon
      val weaponTex = room match {
        case _: PotRoom => Assets.Textures.shopWeaponPot
        case _: TVRoom => Assets.Textures.shopWeaponTv
        case _: RoyalRoom => Assets.Textures.shopWeaponRoyal
      }
      self.draw(weaponTex, 686, 0)
      if (!room.armed && !room.broken) {
        val font =
          if (shared.money >= room.weaponPrice)
            Assets.Fonts.greenFont
          else
            Assets.Fonts.redFont
        font.draw(self, s"~${room.weaponPrice}", 886, 70)
      }
      //Club
      self.draw(Textures.shopClub, 1029, 0)
      val font =
        if (shared.money >= Const.Difficulty.clubPrice)
          Assets.Fonts.greenFont
        else
          Assets.Fonts.redFont
      font.draw(self, s"~${Const.Difficulty.clubPrice}", 1229, 70)
    }
  }

  override def run(delta: Float): Option[Boolean] = {
    render()
    if (Gdx.input.isKeyPressed(Input.Keys.ENTER))
      Some(false)
    else
      None
  }
}
