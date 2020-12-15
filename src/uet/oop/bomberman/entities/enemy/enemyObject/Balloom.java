package uet.oop.bomberman.entities.enemy.enemyObject;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.entities.enemy.Strategy.RandomDirection;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;

/**
 * moves slowly, turning or reversing directions upon colliding with a wall or bomb.
 */
public class Balloom extends Enemy {
    private boolean flag = false;

    public Balloom(int x, int y, Image img, int animationStep) {
        super(x, y, img, animationStep);
        isMoving = true;
    }

    @Override
    protected void updateImage() {
        if (!isAlive()) {
            if (timeLiveLeft == 0) {
                img = null;
                return;
            }
            img = Sprite.dieSprite(Sprite.balloom_dead, Sprite.balloom_dead, Sprite.balloom_dead, timeLiveLeft).getFxImage();
            timeLiveLeft--;
            return;
        }

        switch (direction) {
            case 0:
            case 1:
                if (isMoving) {
                    img = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, animationStep, 60).getFxImage();
                }
                break;
            case 2:
            case 3:
                if (isMoving) {
                    img = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, animationStep, 60).getFxImage();
                }
                break;
        }
    }

    @Override
    public void update() {
        double preX = x;
        double preY = y;

        int timeTryResetDirection = 0;
        while (timeTryResetDirection++ <= 12) {
            double newX = this.getX() + dx[direction] * speed;
            double newY = this.getY() + dy[direction] * speed;

            boolean resetDirection = false;
            if (!isGrass(newX, newY)) {
                resetDirection = true;
            } else if (flag) {
                resetDirection = true;
                break;
            }

            if (resetDirection) {
                direction = RandomDirection.getDirection();
            } else {
                break;
            }
        }

        updateImage();
        updatePosition();

        if (preX == x && preY == y) {
            flag = true;
        }
    }
}
