package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.MovableEntity;
import uet.oop.bomberman.entities.enemy.Strategy.RandomDirection;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Enemy extends MovableEntity {
    protected static int[] dx = {0, 1, 0, -1};
    protected static int[] dy = {-1, 0, 1, 0};

    public Enemy(int x, int y, Image img, int animationStep) {
        super(x, y, img, animationStep);
    }

    @Override
    public boolean isGrass(double newX, double newY) {
        for (int c = 0; c < 4; c++) {
            int _x = (int)((newX + (c % 2) + (Sprite.SCALED_SIZE - 1) * (1 - c % 2)) / Sprite.SCALED_SIZE);
            int _y = (int)((newY + (int) (c / 2) + (Sprite.SCALED_SIZE - 1) * (1 - (int) (c / 2))) / Sprite.SCALED_SIZE);
            if (!(Map.getEntityAtCell(_y, _x) instanceof Grass)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void update() {
        double preX = x;
        double preY = y;
        int preDirection = direction;

        updateImage();
        updatePosition();
        if (x % Sprite.SCALED_SIZE == 0 && y % Sprite.SCALED_SIZE == 0) {
            direction = RandomDirection.getDirection();
        } else if (x == preX && y == preY) {
            while (direction == preDirection) {
                direction = RandomDirection.getDirection();
            }
        }
    }
}
