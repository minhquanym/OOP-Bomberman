package uet.oop.bomberman.entities.enemy.enemyObject;

import javafx.scene.image.Image;
import javafx.util.Pair;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.entities.enemy.Strategy.RandomDirection;
import uet.oop.bomberman.graphics.Sprite;

import java.util.*;

public class Oneal extends Enemy {
    private static final int chasingDistance = 7;
    private boolean flag = false; // flag to control when oneal meet bomb

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

    @Override
    public void update() {
        double preX = x;
        double preY = y;

        if (x % Sprite.SCALED_SIZE == 0 && y % Sprite.SCALED_SIZE == 0) {
            // when nearby player move toward to player (mahattan distance)
            int playerCellX = BombermanGame.player.getCellX();
            int playerCellY = BombermanGame.player.getCellY();
            int distance = Math.abs(getCellX() - playerCellX)
                        + Math.abs(getCellY() - playerCellY);

            if (distance <= chasingDistance && !flag) {
                List<Pair<Integer, Integer>> priorityDirection = new ArrayList<>();
                for (int dir = 0; dir < 4; ++dir) {
                    if (!isGrass(getX() + dx[dir] * speed, getY() + dy[dir] * speed)) {
                        priorityDirection.add(new Pair<Integer, Integer>(100000000, dir));
                        continue;
                    }

                    int newX = (int) getX() + dx[dir] * speed;
                    int newY = (int) getY() + dy[dir] * speed;
                    int newDistance = Math.abs(newX - (int) BombermanGame.player.getX())
                                + Math.abs(newY - (int) BombermanGame.player.getY());
                    priorityDirection.add(new Pair<Integer, Integer>(newDistance, dir));
                }
                Collections.sort(priorityDirection, new Comparator<Pair<Integer, Integer>>() {
                    @Override
                    public int compare(Pair<Integer, Integer> o1, Pair<Integer, Integer> o2) {
                        if (o1.getKey() < o2.getKey()) return -1;
                        if (o1.getKey() > o2.getKey()) return 1;
                        return 0;
                    }
                });

                direction = priorityDirection.get(0).getValue();
            } else {
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
            }
        }

        updateImage();
        updatePosition();

        if (x == preX && y == preY) {
            flag = true;
        }
    }
}
