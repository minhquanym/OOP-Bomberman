package uet.oop.bomberman.entities.enemy.Strategy;

import java.util.Random;

public class RandomDirection {
    private Random generator;
    private static RandomDirection instance;

    private RandomDirection() {
        long currentTime = System.currentTimeMillis();
        generator = new Random(currentTime);
    }

    public static RandomDirection getInstance() {
        if (instance == null) {
            instance = new RandomDirection();
        }
        return instance;
    }

    public static int getDirection() {
        if (instance == null) {
            instance = new RandomDirection();
        }

        int direction = Math.abs(instance.generator.nextInt()) % 4;
        return direction;
    }
}
