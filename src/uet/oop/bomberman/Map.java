package uet.oop.bomberman;

import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.SpriteSheet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import static java.lang.System.exit;

public class Map {
    private static int idLevel;
    private static int numRow;
    private static int numCol;
    private static char[][] gameMap;
    private static Scanner reader;
    private static Entity[][] entityMap;

    Map() {

    }

    public static int getNumRow() {
        return numRow;
    }

    public static int getNumCol() {
        return numCol;
    }

    public static char getValueAtCell(int i, int j) {
        return gameMap[i][j];
    }

    public static Entity getEntityAtCell(int i, int j) {
        return entityMap[i][j];
    }
    static void loadMap() {
        try {
            reader = new Scanner(new File("res/levels/Level1.txt"));
        } catch (FileNotFoundException err) {
            err.printStackTrace();
        }
        idLevel = reader.nextInt();
        numRow = reader.nextInt();
        numCol = reader.nextInt();
        reader.nextLine();

        gameMap = new char[numRow][numCol];
        entityMap = new Entity[numRow][numCol];
        for (int i = 0; i < numRow; ++i) {
            String line = reader.nextLine();
            for (int j = 0; j < numCol; ++j) {
                char c = line.charAt(j);
                int x = j * Sprite.SCALED_SIZE;
                int y = i * Sprite.SCALED_SIZE;
                if (c == '#') {
                    entityMap[i][j] = new Wall(x, y, Sprite.wall.getFxImage());
                } else if (c == '*') {
                    entityMap[i][j] = new Brick(x, y, Sprite.brick.getFxImage());
                } else if (c == 'x') {
                    entityMap[i][j] = new Portal(x, y, Sprite.portal.getFxImage());
                } else {
                    entityMap[i][j] = new Grass(x, y, Sprite.grass.getFxImage());
                }
                gameMap[i][j] = c;
            }
        }

        for (int i = 0; i < numRow; ++i) {
            for (int j = 0; j < numCol; ++j) {
                System.out.print(gameMap[i][j]);
            }
            System.out.println("");
        }
    }

}
