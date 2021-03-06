package com.traptricker.objects;

import com.traptricker.Game;
import com.traptricker.Handler;
import com.traptricker.sound.SoundPlayer;
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;

/**
 * This enemy is very small and fast moving, but does a lot of damage.
 */
public class StreakEnemy extends GameObject {

  private final Handler handler;
  public START_SIDE start_side;

  private static final File whizzSound = new File(
      "src/main/resources/bullet_whizzing_by-Mike_Koenig-2005433595.wav");

  public StreakEnemy(Game game, int x, int y, int xVelocity, int yVelocity, int radius, ID id,
      Handler handler, START_SIDE start_side) {
    super(game, x, y, xVelocity, yVelocity, radius, id);
    this.handler = handler;
    this.start_side = start_side;

    SoundPlayer.playSound(whizzSound, -5f);
  }

  @Override
  public void tick() {
    x += xVelocity;
    y += yVelocity;

    // Kills the enemy after it goes off-screen
    switch (start_side) {
      case up:
        if (x < -10 || x > game.getWidth() || y > game.getHeight()) {
          handler.removeObject(this);
        }
        break;
      case right:
        if (x < -10 || y < -10 || y > game.getHeight()) {
          handler.removeObject(this);
        }
        break;
      case down:
        if (x < -10 || x > game.getWidth() || y < -10) {
          handler.removeObject(this);
        }
        break;
      case left:
        if (x > game.getWidth() || y < -10 || y > game.getHeight()) {
          handler.removeObject(this);
        }
        break;
    }
  }

  @Override
  public void render(Graphics g) {
    g.setColor(Color.orange);
    g.fillRect(x, y, radius * 2, radius * 2);
  }

  public enum START_SIDE {
    up(),
    right(),
    down(),
    left()
  }
}
