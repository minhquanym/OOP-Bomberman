package uet.oop.bomberman.entities.enemy.enemyObject;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.graphics.Sprite;

public class Doll extends Enemy {
    public Doll(int x, int y, Image img, int animationStep) {
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
            img = Sprite.dieSprite(Sprite.doll_dead, Sprite.doll_dead, Sprite.doll_dead, timeLiveLeft).getFxImage();
            timeLiveLeft--;
            return;
        }

        switch (direction) {
            case 0:
            case 1:
                if (isMoving) {
                    img = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, animationStep, 60).getFxImage();
                }
                break;
            case 2:
            case 3:
                if (isMoving) {
                    img = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, animationStep, 60).getFxImage();
                }
                break;
        }
    }

    @Override
    public void update() {
        int prioAddition[] = { 1, -1, 2 };

        int prioID = 0;
        while (prioID < 3) {
            double newX = this.getX() + dx[direction] * speed;
            double newY = this.getY() + dy[direction] * speed;

            boolean resetDirection = false;
            if (!isGrass(newX, newY)) {
                resetDirection = true;
            }

            if (resetDirection) {
                direction = (direction + prioAddition[prioID]) % 4;
                prioID++;
            } else {
                break;
            }
        }

        updateImage();
        updatePosition();
    }
}
