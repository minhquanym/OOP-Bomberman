package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Flame extends Entity {

    private int explosionCountdown = 120;
    private String flameType;
    private boolean done = false;

    public Flame(double x, double y, Image img) {
        super(x, y, img);
    }

    public Flame(double x, double y, Image img, String flameType) {
        super(x, y, img);
        this.flameType = flameType;
    }

    public int getExplosionCountdown() {
        return explosionCountdown;
    }

    public void setExplosionCountdown(int explosionCountdown) {
        this.explosionCountdown = explosionCountdown;
    }

    public String getFlameType() {
        return flameType;
    }

    public void setFlameType(String flameType) {
        this.flameType = flameType;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void explodingImg() {
        if (explosionCountdown == 0 || explosionCountdown >= 30) {
            this.img = null;
            if (explosionCountdown >= 30) {
                explosionCountdown--;
            } else {
                setDone(true);
            }
        } else {
            switch (flameType) {
                case "left":
                case "right": {
                    this.img = Sprite.movingSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1,
                            Sprite.explosion_horizontal2, explosionCountdown, 20).getFxImage();
                    break;
                }
                case "top":
                case "down": {
                    this.img = Sprite.movingSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1,
                            Sprite.explosion_vertical2, explosionCountdown, 20).getFxImage();
                    break;
                }
                case "left_most": {
                    this.img = Sprite.movingSprite(Sprite.explosion_horizontal_left_last, Sprite.explosion_horizontal_left_last1,
                            Sprite.explosion_horizontal_left_last2, explosionCountdown, 20).getFxImage();
                    break;
                }
                case "right_most": {
                    this.img = Sprite.movingSprite(Sprite.explosion_horizontal_right_last, Sprite.explosion_horizontal_right_last1,
                            Sprite.explosion_horizontal_right_last2, explosionCountdown, 20).getFxImage();
                    break;
                }
                case "down_most": {
                    this.img = Sprite.movingSprite(Sprite.explosion_vertical_down_last, Sprite.explosion_vertical_down_last1,
                            Sprite.explosion_vertical_down_last2, explosionCountdown, 20).getFxImage();
                    break;
                }
                case "top_most": {
                    this.img = Sprite.movingSprite(Sprite.explosion_vertical_top_last, Sprite.explosion_vertical_top_last1,
                            Sprite.explosion_vertical_top_last2, explosionCountdown, 20).getFxImage();
                    break;
                }
                case "center": {
                    this.img = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1,
                            Sprite.bomb_exploded2, explosionCountdown, 20).getFxImage();
                    break;
                }
            }
            explosionCountdown--;
        }
    }

    @Override
    public void update() {
        explodingImg();
    }
}
