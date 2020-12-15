package uet.oop.bomberman.entities.enemy.Strategy;

import javafx.util.Pair;
import uet.oop.bomberman.Map;

import java.util.ArrayDeque;
import java.util.Queue;

public class BreadthFirstSearch {
    private static boolean isLoad = false;
    private static int dist[][][][];
    private static int Trace[][][][];
    private static int dx[] = { 0, 1, 0, -1 };
    private static int dy[] = { -1, 0, 1, 0 };

    private static void BFS(int stX, int stY) {
        Queue<Pair<Integer, Integer>> Q = new ArrayDeque<>();
        Q.add(new Pair<>(stX, stY));
        dist[stX][stY][stX][stY] = 1;

        while (Q.size() > 0) {
            int u = Q.peek().getKey();
            int v = Q.peek().getValue();
            Q.remove();

            for (int dir = 0; dir < 4; ++dir) {
                int x = u + dx[dir];
                int y = v + dy[dir];

                if (x < 0 || x >= Map.getNumRow() || y < 0 || y >= Map.getNumCol() ) {
                    continue;
                }
                if (dist[stX][stY][x][y] != 0 || Map.getValueAtCell(x, y) == '#') {
                    continue;
                }

                dist[stX][stY][x][y] = dist[stX][stY][u][v] + 1;
                Trace[stX][stY][x][y] = dir;
                Q.add(new Pair<>(x, y));
            }
        }
    }

    public static void newInstance() {
        if (!isLoad) {
            isLoad = true;
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
        isLoad = false;
    }

    public static int convertCellDirectionToDirection(int direction) {
        switch (direction) {
            case 0:
                return 3;
            case 1:
                return 2;
            case 2:
                return 1;
            case 3:
                return 0;
        }
        assert(0 == 1);
        return -1;
    }

    public static int getDirection(int u, int v, int x, int y) {
        System.out.println(u + " " + v + " || " + x + " " + y + "\n" + Map.getNumRow() + " " + Map.getNumCol());

        if (!isLoad) {
            newInstance();
        }

        if (dist[u][v][x][y] == 0) {
                System.out.println("LAG\n");
            return RandomDirection.getDirection();
        }

        System.out.println(Trace[x][y][u][v] + " --> " + dx[Trace[x][y][u][v]] + " " + dy[Trace[x][y][u][v]]);

        int revDir = (4 - Trace[x][y][u][v]) % 4;
        return convertCellDirectionToDirection(revDir);
    }
}
