package uet.oop.bomberman.entities.enemy.enemyObject;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.entities.enemy.Strategy.BreadthFirstSearch;
import uet.oop.bomberman.entities.enemy.Strategy.RandomDirection;
import uet.oop.bomberman.graphics.Sprite;

public class Kondoria extends Enemy {
    private boolean flag = false;

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
        double preX = x;
        double preY = y;

        if (flag) {
            int timeTryResetDirection = 0;
            while (timeTryResetDirection++ <= 12) {
                double newX = this.getX() + dx[direction] * speed;
                double newY = this.getY() + dy[direction] * speed;

                boolean resetDirection = false;
                if (!isGrass(newX, newY)) {
                    resetDirection = true;
                }

                if (resetDirection) {
                    direction = RandomDirection.getDirection();
                } else {
                    break;
                }
            }

            flag = false;
        } else {
            if (x % Sprite.SCALED_SIZE == 0 && y % Sprite.SCALED_SIZE == 0) {
                int cellX = getCellX();
                int cellY = getCellY();
                int playerCellX = BombermanGame.player.getCellX();
                int playerCellY = BombermanGame.player.getCellY();
                direction = BreadthFirstSearch.getDirection(cellY, cellX, playerCellY, playerCellX);
            }

        }
        updateImage();
        updatePosition();

        if (x == preX && y == preY) {
            flag = true;
        }
    }

    @Override
    public boolean isGrass(double newX, double newY) {
        // brick become grass
        for (int c = 0; c < 4; c++) {
            int _x = (int)((newX + (c % 2) + (Sprite.SCALED_SIZE - 1) * (1 - c % 2)) / Sprite.SCALED_SIZE);
            int _y = (int)((newY + (int) (c / 2) + (Sprite.SCALED_SIZE - 1) * (1 - (int) (c / 2))) / Sprite.SCALED_SIZE);
            if (!(Map.getEntityAtCell(_y, _x) instanceof Grass || Map.getEntityAtCell(_y, _x) instanceof Brick)) {
                return false;
            }
        }
        return true;
    }
}
