package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Bomber extends MovableEntity {
    /*
             0
             ^
             |
     3 <-   (-1)   -> 1
             |
             v
             2
    */
    public List<Bomb> bombList = new ArrayList<Bomb>();
    public int bombRange = 2;
    public int bombLimit = 4;

    private KeyCode keyboardInput;

    public Bomber(int x, int y, Image img) {
        super(x, y, img, 0);
    }

    public Bomber(int x, int y, Image img, int animationStep) {
        super(x, y, img, animationStep);
    }

    public Bomber(int x, int y, Image img, int animationStep, int direction, boolean isMoving, int speed) {
        super(x, y, img, animationStep);
        this.direction = direction;
        this.isMoving = isMoving;
        this.speed = speed;
        this.alive = true;
    }

    public int getBombRange() {
        return bombRange;
    }

    public void setBombRange(int bombRange) {
        this.bombRange = bombRange;
    }

    public int getBombLimit() {
        return bombLimit;
    }

    public void setBombLimit(int bombLimit) {
        this.bombLimit = bombLimit;
    }

    public Entity placeBomb() {
        Entity bomb = new Bomb((int) Math.round(this.getCellX() * Sprite.SCALED_SIZE),
                (int) Math.round(this.getCellY() * Sprite.SCALED_SIZE),
                Sprite.bomb.getFxImage(), this.bombRange);
        this.bombList.add((Bomb) bomb);
        return bomb;
    }
    public void setKeyboardInput(KeyCode keyboardInput) {
        this.keyboardInput = keyboardInput;
    }

    @Override
    public void update() {
        if (!isAlive()) {
            updateImage();
            return;
        }

        if (!bombList.isEmpty()) {
            if (bombList.get(bombList.size() - 1).isExploded()) {
                bombList.remove(bombList.size() - 1);
            }
        }
        isMoving = true;
        if (keyboardInput == KeyCode.LEFT) {
            direction = LeftDirection;
        } else if (keyboardInput == KeyCode.RIGHT) {
            direction = RightDirection;
        } else if (keyboardInput == KeyCode.UP) {
            direction = UpDirection;
        } else if (keyboardInput == KeyCode.DOWN) {
            direction = DownDirection;
        } else {
            isMoving = false;
        }

        animate();
        updatePosition();
        updateImage();

        keyboardInput = null;
    }

    // update image which suits for new direction and new animationStep
    protected void updateImage() {
        if (!isAlive()) {
            if (timeLiveLeft == 0) {
                img = null;
                return;
            }
            img = Sprite.dieSprite(Sprite.player_dead3, Sprite.player_dead2, Sprite.player_dead1, timeLiveLeft).getFxImage();
            timeLiveLeft--;
            return;
        }

        switch (direction) {
            case 0:
                img = Sprite.player_up.getFxImage();
                if (isMoving) {
                    img = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, animationStep, 20).getFxImage();
                }
                break;
            case 1:
                img = Sprite.player_right.getFxImage();
                if (isMoving) {
                    img = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, animationStep, 20).getFxImage();
                }
                break;
            case 2:
                img = Sprite.player_down.getFxImage();
                if (isMoving) {
                    img = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, animationStep, 20).getFxImage();
                }
                break;
            case 3:
                img = Sprite.player_left.getFxImage();
                if (isMoving) {
                    img = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, animationStep, 20).getFxImage();
                }
                break;
        }
    }

    public boolean enemyCollision(List<Entity> entities) {
        if (!this.isAlive()) {
            return false;
        }
        Rectangle playerRectangle = getBoundingBox();
        for (Entity entity : entities) {
            if (!(entity instanceof Enemy)) {
                continue;
            }
            Rectangle enemyRectangle = entity.getBoundingBox();
            if (playerRectangle.intersects(enemyRectangle.getLayoutBounds())) {
                return true;
            }
        }
        return false;
    }
}
