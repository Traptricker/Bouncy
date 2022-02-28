package com.traptricker.objects;

import com.traptricker.Game;
import com.traptricker.Handler;
import com.traptricker.userinterface.HUD;
import java.awt.Color;
import java.util.Random;

/**
 * This class handles the spawning of all the enemies based on certain things like level.
 */
public class Spawner {

  private final Game game;
  private final Handler handler;
  private final HUD hud;
  private final Random random;

  public Spawner(Game game, Handler handler, HUD hud) {
    this.game = game;
    this.handler = handler;
    this.hud = hud;
    this.random = new Random();
  }

  public void tick() {
    // Handles the levels
    switch (hud.level) {
      case 1:
        levelOne();
        break;
      case 2:
        levelTwo();
        break;
      case 3:
        levelThree();
        break;
      case 4:
        levelFour();
        break;
      case 5:
        levelFive();
        break;
      default:
        levelEndless();
    }
  }


  public void spawnTitleScreenEnemy() {
    int radius = random.nextInt(12) + 2;
    int x = random.nextInt(game.getWidth() - 2 * radius);
    int y = random.nextInt(game.getHeight() - 2 * radius);

    // Stops the enemy from spawning with no velocity
    int xVelocity = 0;
    int yVelocity = 0;
    while (xVelocity + yVelocity == 0) {
      xVelocity = random.nextInt(10) - 5;
      yVelocity = random.nextInt(10) - 5;
    }

    float r = random.nextFloat();
    float g = random.nextFloat();
    float b = random.nextFloat();

    handler.addObject(
        new TitleScreenEnemy(game, x, y, xVelocity, yVelocity, radius, ID.TitleScreenEnemy,
            new Color(r, g, b)));
  }

  private void spawnBasicEnemy() {
    int radius = 8;
    // Stops the enemy from spawning too close to the player
    int x = random.nextInt(game.getWidth() - 2 * radius);
    int y = random.nextInt(game.getHeight() - 2 * radius);
    int playerX = 0;
    int playerY = 0;
    for (GameObject object : handler.objects) {
      if (object.getID() == ID.Player) {
        playerX = object.getX();
        playerY = object.getY();
      }
    }
    while ((x - playerX) * (x - playerX) + (y - playerY) * (y - playerY) < 90000) {
      x = random.nextInt(game.getWidth() - 2 * radius);
      y = random.nextInt(game.getHeight() - 2 * radius);
    }

    // Stops the enemy from spawning with no velocity
    int xVelocity = 0;
    int yVelocity = 0;
    while (xVelocity + yVelocity == 0) {
      xVelocity = random.nextInt(10) - 5;
      yVelocity = random.nextInt(10) - 5;
    }

    handler.addObject(new BasicEnemy(game, x, y, xVelocity, yVelocity, radius, ID.BasicEnemy));
  }

  private void spawnStreakEnemy() {
    int radius = 5;
    // Gets a random start_side
    StreakEnemy.START_SIDE start_side = StreakEnemy.START_SIDE.values()[random.nextInt(
        StreakEnemy.START_SIDE.values().length)];
    int x, y, xVelocity, yVelocity;
    // Assigns a semi-random velocity and position based on start_side
    switch (start_side) {
      case up:
        x = random.nextInt(game.getWidth() - (game.getWidth() / 6)) + (game.getWidth() / 12);
        y = -(2 * radius);
        xVelocity = random.nextInt(10) - 5;
        yVelocity = 10;
        break;
      case right:
        x = game.getWidth() + 2 * radius;
        y = random.nextInt(game.getHeight() - (game.getHeight() / 6)) + (game.getHeight() / 12);
        xVelocity = -10;
        yVelocity = random.nextInt(10) - 5;
        break;
      case down:
        x = random.nextInt(game.getWidth() - (game.getWidth() / 6)) + (game.getWidth() / 12);
        y = game.getHeight() + 2 * radius;
        xVelocity = random.nextInt(10) - 5;
        yVelocity = -10;
        break;
      case left:
        x = -(2 * radius);
        y = random.nextInt(game.getHeight() - (game.getHeight() / 6)) + (game.getHeight() / 12);
        xVelocity = 10;
        yVelocity = random.nextInt(10) - 5;
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + start_side);
    }

    handler.addObject(
        new StreakEnemy(game, x, y, xVelocity, yVelocity, radius, ID.StreakEnemy, handler,
            start_side));
  }

  private void spawnHealingEnemy() {
    int radius = 5;
    // Gets a random start_side
    HealingEnemy.START_SIDE start_side = HealingEnemy.START_SIDE.values()[random.nextInt(
        HealingEnemy.START_SIDE.values().length)];
    int x, y, xVelocity, yVelocity;
    // Assigns a semi-random velocity and position based on start_side
    switch (start_side) {
      case up:
        x = random.nextInt(game.getWidth() - (game.getWidth() / 6)) + (game.getWidth() / 12);
        y = -(2 * radius);
        xVelocity = random.nextInt(10) - 5;
        yVelocity = 7;
        break;
      case right:
        x = game.getWidth() + 2 * radius;
        y = random.nextInt(game.getHeight() - (game.getHeight() / 6)) + (game.getHeight() / 12);
        xVelocity = -7;
        yVelocity = random.nextInt(10) - 5;
        break;
      case down:
        x = random.nextInt(game.getWidth() - (game.getWidth() / 6)) + (game.getWidth() / 12);
        y = game.getHeight() + 2 * radius;
        xVelocity = random.nextInt(10) - 5;
        yVelocity = -7;
        break;
      case left:
        x = -(2 * radius);
        y = random.nextInt(game.getHeight() - (game.getHeight() / 6)) + (game.getHeight() / 12);
        xVelocity = 7;
        yVelocity = random.nextInt(10) - 5;
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + start_side);
    }

    handler.addObject(
        new HealingEnemy(game, x, y, xVelocity, yVelocity, radius, ID.HealingEnemy, handler,
            start_side));
  }

  private void spawnHomingEnemy() {
    int radius = 12;
    Player player = null;
    // Stops the enemy from spawning too close to the player
    int x = random.nextInt(game.getWidth() - 2 * radius);
    int y = random.nextInt(game.getHeight() - 2 * radius);
    for (GameObject object : handler.objects) {
      if (object.getID() == ID.Player) {
        player = (Player) object;
      }
    }

    assert player != null;

    int playerX = player.getX();
    int playerY = player.getY();

    while ((x - playerX) * (x - playerX) + (y - playerY) * (y - playerY) < 90000) {
      x = random.nextInt(game.getWidth() - 2 * radius);
      y = random.nextInt(game.getHeight() - 2 * radius);
    }

    handler.addObject(new HomingEnemy(game, x, y, 1, 1, 12, ID.HomingEnemy, player));
  }

  private void spawnInstantDeathEnemy() {
    int radius = 16;
    Player player = null;
    // Stops the enemy from spawning too close to the player
    int x = random.nextInt(game.getWidth() - 2 * radius);
    int y = random.nextInt(game.getHeight() - 2 * radius);
    for (GameObject object : handler.objects) {
      if (object.getID() == ID.Player) {
        player = (Player) object;
      }
    }

    assert player != null;

    int playerX = player.getX();
    int playerY = player.getY();

    while ((x - playerX) * (x - playerX) + (y - playerY) * (y - playerY) < 90000) {
      x = random.nextInt(game.getWidth() - 2 * radius);
      y = random.nextInt(game.getHeight() - 2 * radius);
    }

    handler.addObject(
        new InstantDeathEnemy(game, x, y, 2, 2, radius, ID.InstantDeathEnemy, player, handler));
  }

  private void spawnFireworkEnemy() {
    // radius is the explosion radius of the firework
    int radius = 8 * 20;
    // Gets a random start_corner
    FireworkEnemy.START_CORNER start_corner = FireworkEnemy.START_CORNER.values()[random.nextInt(
        FireworkEnemy.START_CORNER.values().length)];
    int x;
    int y = random.nextInt(game.getHeight() / 3);
    int xVelocity = random.nextInt(6) + 4;
    int yVelocity = 10 * (random.nextInt(10) + 6);
    // Assigns a semi-random velocity and position based on start_corner
    switch (start_corner) {
      case topLeft:
        x = -(2 * radius / 8);
        break;
      case topRight:
        x = game.getWidth() + 2 * radius / 8;
        xVelocity *= -1;
        break;
      case bottomLeft:
        x = -(2 * radius / 8);
        yVelocity *= -1;
        break;
      case bottomRight:
        x = game.getWidth() + 2 * radius / 8;
        xVelocity *= -1;
        yVelocity *= -1;
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + start_corner);
    }

    Player player = null;
    for (GameObject object : handler.objects) {
      if (object.getID() == ID.Player) {
        player = (Player) object;
      }
    }

    assert player != null;
    handler.addObject(new FireworkEnemy(game, x, y, xVelocity, yVelocity, radius, ID.FireworkEnemy,
        player, handler, start_corner, radius / 8));
  }

  private void levelOne() {
    if (hud.getScore() % 100 == 0) {
      spawnBasicEnemy();
    }
  }

  private void levelTwo() {
    if (hud.getScore() % 200 == 0) {
      spawnBasicEnemy();
    }

    if (hud.getScore() % 150 == 0) {
      spawnStreakEnemy();
    }
  }

  private void levelThree() {
    if (hud.getScore() % 200 == 0) {
      spawnBasicEnemy();
    }

    if (hud.getScore() % 100 == 0) {
      spawnStreakEnemy();
    }

    if (hud.getScore() % 500 == 0) {
      spawnFireworkEnemy();
    }

    if (hud.getScore() % 1000 == 0) {
      spawnHomingEnemy();
    }
  }

  private void levelFour() {
    if (hud.getScore() % 400 == 0) {
      spawnBasicEnemy();
    }

    if (hud.getScore() % 300 == 0) {
      spawnStreakEnemy();
    }

    if (hud.getScore() % 300 == 0) {
      spawnHealingEnemy();
    }

    if (hud.getScore() % 400 == 0) {
      spawnFireworkEnemy();
    }

    if (hud.getScore() % 999 == 0) {
      spawnHomingEnemy();
    }
  }

  private void levelFive() {
    if (hud.getScore() % 600 == 0) {
      spawnBasicEnemy();
    }

    if (hud.getScore() % 300 == 0) {
      spawnStreakEnemy();
    }

    if (hud.getScore() % 300 == 0) {
      spawnHealingEnemy();
    }

    if (hud.getScore() % 300 == 0) {
      spawnFireworkEnemy();
    }

    if (hud.getScore() % 999 == 0) {
      spawnHomingEnemy();
      spawnInstantDeathEnemy();
    }
  }

  private void levelEndless() {
    if (hud.getScore() % 500 == 0) {
      spawnBasicEnemy();
    }

    if (hud.getScore() % 300 == 0) {
      spawnStreakEnemy();
    }

    if (hud.getScore() % 300 == 0) {
      spawnHealingEnemy();
    }

    if (hud.getScore() % 300 == 0) {
      spawnFireworkEnemy();
    }

    if (hud.getScore() % 999 == 0) {
      spawnHomingEnemy();
      spawnInstantDeathEnemy();
    }
  }

}
