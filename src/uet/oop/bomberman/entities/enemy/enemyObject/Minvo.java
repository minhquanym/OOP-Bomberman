package uet.oop.bomberman.entities.enemy.enemyObject;

import javafx.scene.image.Image;
import javafx.util.Pair;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.entities.enemy.Strategy.RandomDirection;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * run away from player :)
 */
public class Minvo extends Enemy {
    private final int runAwayDistance = 7;

    public Minvo(int x, int y, Image img, int animationStep) {
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
        if (x % Sprite.SCALED_SIZE == 0 && y % Sprite.SCALED_SIZE == 0) {
            // when nearby player move toward to player (mahattan distance)
            int playerCellX = BombermanGame.player.getCellX();
            int playerCellY = BombermanGame.player.getCellY();
            int distance = Math.abs(getCellX() - playerCellX)
                    + Math.abs(getCellY() - playerCellY);

            if (distance <= runAwayDistance) {
                List<Pair<Integer, Integer>> priorityDirection = new ArrayList<>();
                for (int dir = 0; dir < 4; ++dir) {
                    if (!isGrass(getX() + dx[dir] * speed, getY() + dy[dir] * speed)) {
                        priorityDirection.add(new Pair<Integer, Integer>(1000000, dir));
                        continue;
                    }

                    int newX = (int) getX() + dx[dir] * speed;
                    int newY = (int) getY() + dy[dir] * speed;
                    int newDistance = Math.abs(newX - (int) BombermanGame.player.getX())
                            + Math.abs(newY - (int) BombermanGame.player.getY());
                    priorityDirection.add(new Pair<Integer, Integer>(-newDistance, dir));
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
                direction = RandomDirection.getDirection();
            }
        }

        updateImage();
        updatePosition();
    }
}
