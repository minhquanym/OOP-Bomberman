package uet.oop.bomberman;

import uet.oop.bomberman.graphics.SpriteSheet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import static java.lang.System.exit;

public class Map {
    private int idLevel;
    private int numRow;
    private int numCol;
    private char[][] gameMap;
    private Scanner reader;

    Map() {
        try {
            reader = new Scanner(new File("res/levels/Level1.txt"));
        } catch (FileNotFoundException err) {
            err.printStackTrace();
        }
    }

    public int getNumRow() {
        return numRow;
    }

    public int getNumCol() {
        return numCol;
    }

    public char getValueAtCell(int i, int j) {
        return gameMap[i][j];
    }

    void loadMap() {
        idLevel = reader.nextInt();
        numRow = reader.nextInt();
        numCol = reader.nextInt();
        reader.nextLine();

        gameMap = new char[numCol][numRow];
        for (int i = 0; i < numRow; ++i) {
            String line = reader.nextLine();
            for (int j = 0; j < numCol; ++j) {
                gameMap[j][i] = line.charAt(j);
            }
        }

        for (int i = 0; i < numRow; ++i) {
            for (int j = 0; j < numCol; ++j) {
                System.out.print(gameMap[j][i]);
            }
            System.out.println("");
        }
    }
}
