package uet.oop.bomberman.entities.enemy.Strategy;

import com.sun.jmx.remote.internal.ArrayQueue;
import javafx.util.Pair;
import uet.oop.bomberman.Map;

import java.util.ArrayDeque;
import java.util.Queue;

public class BreadthFirstSearch {
    private boolean isNull = false;
    private static int dist[][][][];
    private static Pair<Integer, Integer> Trace[][][][];
    private static int h[] = { -1, 0, 1, 0 };
    private static int c[] = { 0, 1, -1, 0 };

    void BFS(int stX, int stY) {
        Queue<Pair<Integer, Integer>> Q = new ArrayDeque<>();
        Q.add(new Pair<>(stX, stY));
        dist[stX][stY][stX][stY] = 1;

        while (Q.size() > 0) {
            int u = Q.peek().getKey();
            int v = Q.peek().getValue();

            for (int dir = 0; dir < 4; ++dir) {
                int x = u + h[dir];
                int y = v + c[dir];
                if (x < 0 || x > Map.getNumRow() || y < 0 || y > Map.getNumCol() || dist[stX][stY][x][y] != 0) {
                    continue;
                }

                dist[stX][stY][x][y] = dist[stX][stY][u][v] + 1;
            }
        }
    }

    void newInstance() {
        if (!isNull) {
            isNull = true;
            dist = new int[Map.getNumRow()][Map.getNumCol()][Map.getNumRow()][Map.getNumCol()];

            for (int i = 0; i < Map.getNumRow(); ++i) {
                for (int j = 0; j < Map.getNumCol(); ++j) {
                    BFS(i, j);
                }
            }
        }
    }

    void removeInstance() {
        isNull = false;
    }

    int getDirection(int u, int v, int x, int y) {

    }
}
