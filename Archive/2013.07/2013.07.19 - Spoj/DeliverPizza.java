package Tasks;

import javaUtils.*;

import java.util.Arrays;
import java.util.PriorityQueue;

public class DeliverPizza {
    int[] dx = MiscUtils.DX4;
    int[] dy = MiscUtils.DY4;
    char[][] table;
    int[][] dist;
    int n, m;

    void dijkstra(int sr, int sc) {
        PriorityQueue<Node> pq = new PriorityQueue<Node>();

        dist[sr][sc] = 0;
        boolean fin[][] = new boolean[n][m];
        pq.add(new Node(sr, sc));
        int done = 0;
        while (done < n * m) {
            if (pq.size() == 0) return;
            Node pop = pq.poll();
            fin[pop.x][pop.y] = true;
            //System.out.println("popped " + pop.x + "  " + pop.y);
            //if (dist[pop.x][pop.y] > pop.d) dist[pop.x][pop.y] = pop.d;
            for (int i = 0; i < 4; i++) {
                int tx = pop.x + dx[i];
                int ty = pop.y + dy[i];
                try {
                    int c = getCost(table[pop.x][pop.y], table[tx][ty]);
                    if (c != -1) {
                        if (dist[tx][ty] > dist[pop.x][pop.y] + c) dist[tx][ty] = dist[pop.x][pop.y] + c;
                        if (!fin[tx][ty]) {
                            pq.add(new Node(tx, ty));
                            //System.out.println("PUSH " + tx + "  " + ty);
                            fin[tx][ty] = true;
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }
            }
            done++;
        }

    }

    int getCost(char sq1, char sq2) {
        if (sq1 == '$' || sq2 == '$') return 2;
        if (sq1 == 'X') return 2;
        switch (Math.abs(sq1 - sq2)) {
            case 0:
                return 1;
            case 1:
                return 3;
            default:
                return -1;
        }
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        n = in.readInt();
        m = in.readInt();

        table = IOUtils.readTable(in, n, m);
        dist = new int[n][m];
        ArrayUtils.fill2d(dist, 10000);
        int sr = -1, sc = -1;
        int buildingCount = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (table[i][j] == 'X') {
                    sr = i;
                    sc = j;
                } else if (table[i][j] == '$') buildingCount++;
            }
        }
        int ans = 10000000;
        if (sr == -1 || sc == -1 || buildingCount == 0) {
            out.printLine(0);
            return;
        }
        dijkstra(sr, sc);
        boolean ok = true;
        int[] distances = new int[buildingCount];
        int done = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (table[i][j] == '$') {
                    distances[done++] = dist[i][j];
                    ok &= (dist[i][j] != 10000);
                }
            }
        }
        if (!ok) {
            out.printLine(-1);
            return;
        }
        /*
        debug

        distances = new int[]{20,14,6,5,4};
        buildingCount = distances.length;
        */
        if (buildingCount == 0) out.printLine(0);
        else if (buildingCount == 1) out.printLine(distances[0]);
        else if (buildingCount == 2) out.printLine(Math.max(distances[0], distances[1]));
        else {
            Arrays.sort(distances);
            for (int mask = 1; mask < (1 << buildingCount); mask++) {
                int sum1 = 0, sum2 = 0;
                int last0 = 0, last1 = 0;
                for (int i = 0; i < buildingCount; i++) {
                    if (((1 << i) & mask) != 0) {
                        last1 = i;
                        sum1 += distances[i];
                    } else {
                        last0 = i;
                        sum2 += distances[i];
                    }
                }
                sum1 = (sum1 << 1) - distances[last1];
                sum2 = (sum2 << 1) - distances[last0];
                ans = Math.min(ans, Math.max(sum1, sum2));
            }
            out.printLine(ans);
        }
    }

    class Node implements Comparable<Node> {
        int x, y;

        Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Node o) {
            return dist[x][y] - dist[o.x][o.y];
        }
    }
}
