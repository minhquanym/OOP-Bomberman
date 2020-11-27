package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.MovableEntity;
import uet.oop.bomberman.entities.enemy.moveStrategy.MoveStrategy;
import uet.oop.bomberman.entities.enemy.moveStrategy.RandomStrategy;

public abstract class Enemy extends MovableEntity {
    protected MoveStrategy enemyAI;

    public Enemy(int x, int y, Image img, int animationStep) {
        super(x, y, img, animationStep);
    }
}
