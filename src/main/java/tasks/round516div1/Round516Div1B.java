package tasks.round516div1;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Round516Div1B {
    private static Scanner in = new Scanner(System.in);
    
    private static int n, m, r, c, x, y;
    private static Cell[][] labyrinth;

    public static void resolve(){
        input();

        output(findAllReachableCell());
    }

    private static Queue<Coordinates> coordinates;

    private static void input(){
        n = in.nextInt();
        m = in.nextInt();
        r = in.nextInt();
        c = in.nextInt();
        x = in.nextInt();
        y = in.nextInt();
        labyrinth = new Cell[n][];
        coordinates = new LinkedList<>(); // maybe ArrayList;

        for (int i = 0; i < n; i ++) {
            labyrinth[i] = new Cell[m];
            String l = in.nextLine();
            for (int j = 0; j < m; j++) {
                labyrinth[i][j] = new Cell(l.charAt(j));
            }
        }
    }

    private static int findAllReachableCell() {
        int result = 0;
        Coordinates start = new Coordinates(r - 1, c - 1);
        labyrinth[r - 1][c - 1].addOrUpdate(x, y);
        coordinates.add(start);
        int[][] steps = {{0,-1}, {1,0}, {0,1}, {-1,0}};
        WASD[] wasds = {WASD.LEFT, WASD.UPDOWN, WASD.RIGHT, WASD.UPDOWN};
        while (!coordinates.isEmpty()) {
            Coordinates cur = coordinates.poll();
            if (!labyrinth[cur.row][cur.column].isVisited) {
                result++;
                labyrinth[cur.row][cur.column].isVisited = true;
            }
//            System.out.printf("CUR{%d, %d} :", cur.row, cur.column);
            for (int i = 0; i < 4; i++) {
                tryAddToQueue(cur, steps[i], wasds[i]);
            }
//            queueMonitor();
        }

        return result;
    }

    private static void queueMonitor() {
        for (Coordinates c: coordinates) {
            System.out.printf("{%d, %d},", c.row, c.column);
        }
        System.out.println();
    }

    private static boolean tryAddToQueue (Coordinates c, int[] delta, WASD wasd) {
        int newRow = c.row + delta[0];
        int newColumn = c.column + delta[1];
        if (
                (
                        newRow < 0 ||
                                newColumn < 0 ||
                                newRow > n - 1 ||
                                newColumn > m - 1 ||
                                labyrinth[newRow][newColumn].val == '*'
                ) ||
                        (
                                wasd == WASD.LEFT && labyrinth[c.row][c.column].x == 0
                        ) ||
                        (
                                wasd == WASD.RIGHT && labyrinth[c.row][c.column].y == 0
                        )
        ) {
            return false;
        }

        if (isNeedQueue(labyrinth[c.row][c.column], labyrinth[newRow][newColumn], wasd)) {
            if (wasd == WASD.LEFT) {
                labyrinth[newRow][newColumn].addOrUpdate(labyrinth[c.row][c.column].x - 1, labyrinth[c.row][c.column].y);
            } else if (wasd == WASD.RIGHT) {
                labyrinth[newRow][newColumn].addOrUpdate(labyrinth[c.row][c.column].x, labyrinth[c.row][c.column].y - 1);
            } else {
                labyrinth[newRow][newColumn].addOrUpdate(labyrinth[c.row][c.column].x, labyrinth[c.row][c.column].y);
            }

            coordinates.add(new Coordinates(newRow, newColumn));

            return true;
        } else {
            return false;
        }
    }

    private static boolean isNeedQueue(Cell current, Cell newCell, WASD wasd) {
        return (newCell.from == current) ||
                (wasd == WASD.LEFT && (newCell.x < current.x - 1 || newCell.y < current.y)) ||
                (wasd == WASD.RIGHT && (newCell.x < current.x || newCell.y < current.y - 1)) ||
                (wasd == WASD.UPDOWN && (newCell.x < current.x || newCell.y < current.y));
    }

    private enum WASD {
        LEFT,
        RIGHT,
        UPDOWN
    }

    private static class Coordinates {
        int row;
        int column;

        public Coordinates(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    private static class Cell {
        char val;
        boolean isVisited = false;
        int x = -1;
        int y = -1;
        Cell from;

        Cell(char val) {
            this.val = val;
        }

        public boolean addOrUpdate(int x, int y) {
            if (this.x == -1) {
                this.x = x;
                this.y = y;
                return true;
            }

            this.x = Math.max(x, this.x);
            this.y = Math.max(y, this.y);

            return false;
        }
    }

    private static void output(int v){
//        out.println(v); // просто поставить вывод
    }
}
