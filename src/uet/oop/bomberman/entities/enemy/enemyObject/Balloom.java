package uet.oop.bomberman.entities.enemy.enemyObject;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.entities.enemy.moveStrategy.RandomStrategy;
import uet.oop.bomberman.graphics.Sprite;

public class Balloom extends Enemy {
    public Balloom(int x, int y, Image img, int animationStep) {
        super(x, y, img, animationStep);
        enemyAI = new RandomStrategy();
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
        direction = enemyAI.getDirection();
//        if (!isAlive()) {
//            isMoving = false;
//        }

        updateImage();
        updatePosition();
    }
}
