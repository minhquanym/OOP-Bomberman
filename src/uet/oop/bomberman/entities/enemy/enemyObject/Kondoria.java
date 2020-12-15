package uet.oop.bomberman.entities.enemy.enemyObject;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.entities.enemy.Strategy.BreadthFirstSearch;
import uet.oop.bomberman.graphics.Sprite;

public class Kondoria extends Enemy {
    public Kondoria(int x, int y, Image img, int animationStep) {
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
            img = Sprite.dieSprite(Sprite.kondoria_dead, Sprite.kondoria_dead, Sprite.kondoria_dead, timeLiveLeft).getFxImage();
            timeLiveLeft--;
            return;
        }

        switch (direction) {
            case 0:
            case 1:
                if (isMoving) {
                    img = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3, animationStep, 60).getFxImage();
                }
                break;
            case 2:
            case 3:
                if (isMoving) {
                    img = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3, animationStep, 60).getFxImage();
                }
                break;
        }
    }

    @Override
    public void update() {
                System.out.println(x + " " + y + "\n");

        if (x % Sprite.SCALED_SIZE == 0 && y % Sprite.SCALED_SIZE == 0) {
            int cellX = getCellX();
            int cellY = getCellY();
            int playerCellX = BombermanGame.player.getCellX();
            int playerCellY = BombermanGame.player.getCellY();
            direction = BreadthFirstSearch.getDirection(cellX, cellY, playerCellX, playerCellY);
        }

        updateImage();
        updatePosition();
    }
}
