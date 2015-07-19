package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.ArrayList;

public class EditDistanceOnGrid {
    int n, m, cost[];

    public void solve(int testNumber, InReader in, OutputWriter out) {
        int r = in.readInt();
        int c = in.readInt();
        cost = new int[]{1, in.readInt(), in.readInt()};

        n = r;
        m = c;

        char[][] mat = IOUtils.readTable(in, r, c);

        int[][] map = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (mat[i][j] == 'W') map[i][j] = 0;
                else map[i][j] = 1;
            }
        }

        Algorithm1 algo = new Algorithm1(map);
        ArrayList<Operation> ans = algo.solveIt();
        assert (ans.size() < 1000000);
        out.printLine(ans.size());
        for (Operation op : ans) {
            out.printLine(op.toString());
        }

    }

    boolean rangeCheck(int r, int c) {
        return 0 <= r && r < n && 0 <= c && c < m;
    }

    enum Directions {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    class Operation {

    }

    class SwapOperation extends Operation {
        int r1, c1;
        int r2, c2;

        SwapOperation(int r1, int c1, int r2, int c2) {
            assert (rangeCheck(r1, c1) && rangeCheck(r2, c2));
            assert ((r1 == r2 && Math.abs(c1 - c2) == 1) || (c1 == c2 && Math.abs(r1 - r2) == 1));
            this.r1 = r1 + 1;
            this.r2 = r2 + 1;
            this.c1 = c1 + 1;
            this.c2 = c2 + 1;
        }


        @Override
        public String toString() {
            return "1 " + r1 + " " + c1 + " " + r2 + " " + c2;
        }
    }

    class W2BOperation extends Operation {
        int r, c;

        W2BOperation(int r, int c) {
            assert (rangeCheck(r, c));
            this.c = c + 1;
            this.r = r + 1;
        }

        @Override
        public String toString() {
            return "2 " + r + " " + c;
        }
    }

    class B2WOperation extends Operation {
        int r, c;

        B2WOperation(int r, int c) {
            assert (rangeCheck(r, c));
            this.c = c + 1;
            this.r = r + 1;
        }

        @Override
        public String toString() {
            return "3 " + r + " " + c;
        }
    }

    class Algorithm1 {
        int[][] map;
        ArrayList<Operation> ops = new ArrayList<Operation>();

        int totalCost[][], lo[][], hi[][];
        ArrayList<Operation> steps[][];

        Algorithm1(int[][] map) {
            this.map = map;
        }

        public void columnConnect(final int c, final int median) {
            ArrayList<Integer> up = new ArrayList<Integer>();
            for (int i = median - 1; i >= 0; i--) {
                if (map[i][c] == 1) up.add(i);
            }
            ArrayList<Integer> down = new ArrayList<Integer>();
            for (int i = median; i < n; i++) {
                if (map[i][c] == 1) down.add(i);
            }

            if (up.size() + down.size() == 0) {
                totalCost[c][median] = -1;
                lo[c][median] = -1;
                hi[c][median] = -1;
                return;
            }
            steps[c][median] = new ArrayList<Operation>();

            int target;
            if (map[median][c] == 0 && down.size() == 0) target = median;
            else {
                target = median - 1;
            }

            for (int r : up) {
                /*if(target - r >= cost[2]) {
                    steps[c][median].add(new B2WOperation(r, c));
                    totalCost[c][median] += cost[2];
                }
                else {*/
                while (r < target) {
                    steps[c][median].add(new SwapOperation(r, c, r + 1, c));
                    totalCost[c][median] += cost[0];
                    r++;
                }
                target--;
                //}
            }
            lo[c][median] = target + 1;

            target = median;

            for (int r : down) {
                /*if(r - target >= cost[2]) {
                    steps[c][median].add(new B2WOperation(r, c));
                    totalCost[c][median] += cost[2];
                }
                else { */
                while (r > target) {
                    steps[c][median].add(new SwapOperation(r, c, r - 1, c));
                    totalCost[c][median] += cost[0];
                    r--;
                }
                target++;
                //}
            }
            if (target == median) hi[c][median] = median;
            else hi[c][median] = target - 1;
        }

        public ArrayList<Operation> solveIt() {
            this.map = map.clone();

            totalCost = new int[m][n];
            lo = new int[m][n];
            hi = new int[m][n];
            steps = new ArrayList[m][n];

            for (int median = 0; median < n; median++) {
                for (int column = 0; column < m; column++) {
                    columnConnect(column, median);
                    /*System.out.println(column + " : " + median);
                    if(steps[column][median] != null)
                        for(Operation op : steps[column][median])
                            System.out.println(op.toString());*/
                }
            }

            int lowestCost = Integer.MAX_VALUE;
            ArrayList<Operation> ans = new ArrayList<Operation>();

            for (int median = 0; median < n; median++) {
                int startCol = -1;
                for (int j = 0; j < m; j++) {
                    if (totalCost[j][median] != -1) {
                        startCol = j;
                        break;
                    }
                }
                int endCol = -1;
                for (int j = m - 1; j >= 0; j--) {
                    if (totalCost[j][median] != -1) {
                        endCol = j;
                        break;
                    }
                }
                if (startCol == -1) continue;
                int currentCost = 0;
                ArrayList<Operation> rival = new ArrayList<Operation>();
                for (int i = startCol; i <= endCol; i++) {
                    if (totalCost[i][median] != -1) {
                        currentCost += totalCost[i][median];
                        rival.addAll(steps[i][median]);
                    } else {
                        currentCost += cost[1];
                        rival.add(new W2BOperation(median, i));
                    }
                }
                if (currentCost < lowestCost) {
                    ans = rival;
                    lowestCost = currentCost;
                }
            }

            return ans;
        }
    }
}

/*
5 5 1 2
WBWWW
WBBBB
BBBBB
BWWBB
BWWWW
 */


