package uet.oop.bomberman.entities.enemy.enemyObject;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.entities.enemy.moveStrategy.RandomStrategy;
import uet.oop.bomberman.graphics.Sprite;

public class Doll extends Enemy {
    public Doll(int x, int y, Image img, int animationStep) {
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
        direction = enemyAI.getDirection();
        updateImage();
        updatePosition();
    }
}
