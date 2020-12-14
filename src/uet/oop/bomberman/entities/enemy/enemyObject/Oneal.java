package uet.oop.bomberman.entities.enemy.enemyObject;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.graphics.Sprite;

public class Oneal extends Enemy {
    public Oneal(int x, int y, Image img, int animationStep) {
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
            img = Sprite.dieSprite(Sprite.oneal_dead, Sprite.oneal_dead, Sprite.oneal_dead, timeLiveLeft).getFxImage();
            timeLiveLeft--;
            return;
        }

        switch (direction) {
            case 0:
            case 1:
                if (isMoving) {
                    img = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, animationStep, 60).getFxImage();
                }
                break;
            case 2:
            case 3:
                if (isMoving) {
                    img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, animationStep, 60).getFxImage();
                }
                break;
        }
    }

//    @Override
//    public void update() {
//        direction = enemyAI.getDirection();
//        updateImage();
//        updatePosition();
//    }
}
