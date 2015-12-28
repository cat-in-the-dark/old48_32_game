package com.catinthedark.sszb

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.{InputAdapter, Input, Gdx}
import com.catinthedark.sszb.common.Const
import com.catinthedark.sszb.common.Const.UI
import com.catinthedark.sszb.entity.{RoyalRoom, TVRoom, PotRoom, Room}
import com.catinthedark.lib.YieldUnit
import com.catinthedark.lib.Magic._
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

  def buyClub() = {
    if (shared.money >= Const.Difficulty.clubPrice) {
      shared.isClubBought = true
      shared.money = Const.Difficulty.clubPrice
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
    Assets.Audios.bgmBirds.play()
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
          case Input.Keys.NUM_4 => buyClub()
          case _ =>
        }

        println(shared.money, currentRoom, shared.rooms(currentRoom._1)(currentRoom._2))
        true
      }
    })
  }

  val batch = new SpriteBatch

  override def onExit(): Unit = {
    Assets.Audios.bgmBirds.stop()
    shared.lvl += 1
  }

  var frameKF = 0f

  def render(delta: Float): Unit = {
    batch.managed { self =>
      self.draw(Textures.bgDay, 0, 0)
      self.draw(Textures.clublightDay, Const.Physics.clubXPos, Const.Physics.clubYPos)
      for (i <- shared.rooms.indices;
           j <- shared.rooms(0).indices) {

        val room = shared.rooms(i)(j)

        if (!room.broken && room.armed)
          room match {
            case r: PotRoom =>
              self.draw(Assets.Textures.wndBackPot, j * 128 + 128 + 55, i * 128 + 256 + 30)
            case r: TVRoom =>
              self.draw(Assets.Textures.wndBackTv, j * 128 + 128 + 50, i * 128 + 256 + 30)
            case r: RoyalRoom =>
              self.draw(Assets.Textures.wndBackRoyal, j * 128 + 128 + 60, i * 128 + 256 + 30)
            case _ =>
          }

        val bgTex = room match {
          case r: Room if !r.broken && r.bought =>
            Some(Assets.Textures.lightOn)
          case r: Room if !r.broken && !r.bought =>
            Some(Assets.Textures.lightDay)
          case _ => None
        }

        bgTex.foreach { tex =>
          self.draw(tex, j * 128 + 128, i * 128 + 256)
        }

        val tex = if (room.broken) Textures.wndDayBroken
        else if (room.grate) Textures.wndDayGrate
        else if (room.bought) Textures.wndDayNormal
        else Textures.wndDayNotBought

        self.draw(tex, j * 128 + 128, i * 128 + 256)

      }

      Assets.Fonts.moneyFrontFont.draw(self, "~: " + s"${shared.money}", UI.moneyPos.x, UI.moneyPos.y)

      //Bye/Repair
      val (x, y) = currentRoom
      self.draw(Assets.Animations.frame.getKeyFrame(frameKF), y * 128 + 128, x * 128 + 256)
      frameKF += delta
      val room = shared.rooms(x)(y)
      if (!room.bought) {
        self.draw(Textures.shopBye, 0, 0)
        val font =
          if (shared.money >= room.buyPrice)
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
      if (!room.grate && !room.broken && room.bought) {
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
      if (!room.armed && !room.broken && room.bought) {
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
    render(delta)
    if (Gdx.input.isKeyPressed(Input.Keys.ENTER) || shared.isClubBought)
      Some(shared.isClubBought)
    else
      None
  }
}
