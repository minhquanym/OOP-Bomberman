package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends Entity {
    private int bombLevel = 2;
    private List<Entity> flames = new ArrayList<>();
    private boolean done = false;
    private boolean exploded = false;
    private int explosionCountdown = 15;
    private int tickingCountdown = 90;

    public int getBombLevel() {
        return bombLevel;
    }

    public void setBombLevel(int bombLevel) {
        this.bombLevel = bombLevel;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isExploded() {
        return exploded;
    }

    public void setExploded(boolean exploded) {
        this.exploded = exploded;
    }

    public int getExplosionCountdown() {
        return explosionCountdown;
    }

    public void setExplosionCountdown(int explosionCountdown) {
        this.explosionCountdown = explosionCountdown;
    }

    public int getTickingCountdown() {
        return tickingCountdown;
    }

    public void setTickingCountdown(int tickingCountdown) {
        this.tickingCountdown = tickingCountdown;
    }

    public List<Entity> getFlames() {
        return flames;
    }

    public void setFlames() {
        String[] flameType = {"left", "down", "right", "top", "left_most", "down_most", "right_most", "top_most", "center"};
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, 1, 0, -1};

        flames.add(new Flame(x, y, null, "center"));
        for (int direction = 0; direction < 4; direction++) {
            for (int i = 1; i <= bombLevel; i++) {
                double newX = x + dx[direction] * Sprite.SCALED_SIZE * i;
                double newY = y + dy[direction] * Sprite.SCALED_SIZE * i;

                if (i == bombLevel) {
                    flames.add(new Flame(newX, newY, null, flameType[direction + 4]));
                } else {
                    flames.add(new Flame(newX, newY, null, flameType[direction]));
                }
                Entity tmp = Map.getEntityAtCoordinate(newX, newY);
                if (tmp instanceof Brick || tmp instanceof Wall) {
                    if (tmp instanceof Brick) {
                    }
                    break;
                }
            }
        }
    }

    public Bomb(int x, int y, Image img) {
        super(x, y, img);
        setFlames();
    }

    public Bomb(int x, int y, Image img, int bombLevel) {
        super(x, y, img);
        this.bombLevel = bombLevel;
        setFlames();
    }

    private void tickingImg() {
        if (tickingCountdown == 0) {
            setExploded(true);
        } else {
            this.img = Sprite
                    .movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, tickingCountdown, 20)
                    .getFxImage();
            tickingCountdown--;
        }
    }
    private void explodingImg() {
        if (explosionCountdown == 0) {
            setDone(true);
            this.img = null;
        } else {
            this.img = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1,
                    Sprite.bomb_exploded2, explosionCountdown, 20).getFxImage();
            explosionCountdown--;
        }
    }
    @Override
    public void update() {
        if (!isExploded()) {
            tickingImg();
        } else {
            explodingImg();
        }
    }

    
}
