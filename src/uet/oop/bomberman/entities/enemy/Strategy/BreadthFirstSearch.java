package uet.oop.bomberman.entities.enemy.Strategy;

import javafx.util.Pair;
import uet.oop.bomberman.Map;

import java.util.ArrayDeque;
import java.util.Queue;

public class BreadthFirstSearch {
    private static boolean isNull = false;
    private static int dist[][][][];
    private static int Trace[][][][];
    private static int h[] = { -1, 0, 1, 0 };
    private static int c[] = { 0, 1, -1, 0 };

    private static void BFS(int stX, int stY) {
        Queue<Pair<Integer, Integer>> Q = new ArrayDeque<>();
        Q.add(new Pair<>(stX, stY));
        dist[stX][stY][stX][stY] = 1;

        while (Q.size() > 0) {
            int u = Q.peek().getKey();
            int v = Q.peek().getValue();
            Q.remove();

            for (int dir = 0; dir < 4; ++dir) {
                int x = u + h[dir];
                int y = v + c[dir];
                if (x < 0 || x > Map.getNumRow() || y < 0 || y > Map.getNumCol() || dist[stX][stY][x][y] != 0 || Map.getValueAtCell(y, x) == '#') {
                    continue;
                }

                dist[stX][stY][x][y] = dist[stX][stY][u][v] + 1;
                Trace[stX][stY][x][y] = dir;
                Q.add(new Pair<>(x, y));
            }
        }
    }

    public static void newInstance() {
        if (!isNull) {
            isNull = true;
            dist = new int[Map.getNumRow()][Map.getNumCol()][Map.getNumRow()][Map.getNumCol()];
            Trace = new int[Map.getNumRow()][Map.getNumCol()][Map.getNumRow()][Map.getNumCol()];

            for (int i = 0; i < Map.getNumRow(); ++i) {
                for (int j = 0; j < Map.getNumCol(); ++j) {
                    BFS(i, j);
                }
            }
        }
    }

    public static void removeInstance() {
        isNull = false;
    }

    public static int getDirection(int u, int v, int x, int y) {
        if (isNull) {
            newInstance();
        }

        if (dist[u][v][x][y] == -1) {
            return RandomDirection.getDirection();
        }

        int revDir = Trace[x][y][u][v];
        return revDir;
    }
}
