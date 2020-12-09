package uet.oop.bomberman.entities.enemy.enemyObject;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.entities.enemy.moveStrategy.BalloomStrategy;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;

/**
 * moves slowly, turning or reversing directions upon colliding with a wall or bomb.
 */
public class Balloom extends Enemy {
    private List<Entity> listBomb;

    public Balloom(int x, int y, Image img, int animationStep) {
        super(x, y, img, animationStep);
        enemyAI = BalloomStrategy.getInstance();
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
        int[] dx = {0, 1, 0, -1};
        int[] dy = {-1, 0, 1, 0};

        int timeTryResetDirection = 0;
        while (timeTryResetDirection <= 12) {
            double newX = this.getX() + dx[direction] * speed;
            double newY = this.getY() + dy[direction] * speed;

            boolean resetDirection = false;
            if (!isGrass(newX, newY)) {
                resetDirection = true;
            }

//            if (!resetDirection) {
//                for (Entity entity : BombermanGame.bombs) {
//                    Rectangle rectangle = new Rectangle(newX + Entity.widthEps, newY + Entity.heightEps,
//                            Sprite.SCALED_SIZE - Entity.widthEps, Sprite.SCALED_SIZE - Entity.heightEps);
//                    if (rectangle.intersects(entity.getBoundingBox().getLayoutBounds())) {
//                        resetDirection = true;
//                        break;
//                    }
//                }
//            }

            if (resetDirection) {
                direction = enemyAI.getDirection();
            } else {
                break;
            }
        }

        updateImage();
        updatePosition();
    }
}
