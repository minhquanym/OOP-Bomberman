package uet.oop.bomberman.entities.enemy.enemyObject;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.entities.enemy.moveStrategy.RandomStrategy;
import uet.oop.bomberman.graphics.Sprite;

public class Minvo extends Enemy {
    public Minvo(int x, int y, Image img, int animationStep) {
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
            img = Sprite.dieSprite(Sprite.minvo_dead, Sprite.minvo_dead, Sprite.minvo_dead, timeLiveLeft).getFxImage();
            timeLiveLeft--;
            return;
        }

        switch (direction) {
            case 0:
            case 1:
                if (isMoving) {
                    img = Sprite.movingSprite(Sprite.minvo_right1, Sprite.minvo_right2, Sprite.minvo_right3, animationStep, 60).getFxImage();
                }
                break;
            case 2:
            case 3:
                if (isMoving) {
                    img = Sprite.movingSprite(Sprite.minvo_left1, Sprite.minvo_left2, Sprite.minvo_left3, animationStep, 60).getFxImage();
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
