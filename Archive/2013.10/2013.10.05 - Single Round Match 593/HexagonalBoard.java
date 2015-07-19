package Tasks;

import java.util.ArrayList;
import java.util.Arrays;

public class HexagonalBoard {

    ArrayList<Integer> adj[];
    int n;
    int[] map;
    int[] col;

    boolean three = false, two = false;

    int[] dx = {-1, -1, 0, 1, 1, 0};
    int[] dy = {0, 1, 1, 0, -1, -1};

    void dfs(int v, int c, int p) {
        if (adj[v].size() == 0) return;
        if (map[v] == -1) map[v] = c;
        else if (map[v] == c) return;
        else {
            three = true;
            map[v] = 2;
            //System.out.println("v/n = " + v / n);
            //System.out.println("v % n = " + v % n);
            return;
        }
        for (int ch : adj[v]) {
            if (ch != p) {
                two = true;
                dfs(ch, 1 - c, v);
            }
        }
    }

    public int minColors(String[] board) {
        n = board.length;
        adj = new ArrayList[n * n];

        for (int i = 0; i < n * n; i++) {
            adj[i] = new ArrayList<Integer>();
        }

        map = new int[n * n];

        Arrays.fill(map, -1);

        boolean one = false;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int v = n * i + j;
                if (board[i].charAt(j) == '-') continue;
                one = true;
                for (int k = 0; k < 6; k++) {
                    int ni = i + dx[k];
                    int nj = j + dy[k];
                    if (ni >= 0 && ni < n && nj >= 0 && nj < n) {
                        if (board[ni].charAt(nj) == 'X') {

                            int u = n * ni + nj;
                            adj[v].add(u);
                            adj[u].add(v);
                        }
                    }
                }
            }
        }

        for (int i = 0; i < n * n; i++) {
            if (map[i] == -1) {
                //Arrays.fill(map, -1);
                dfs(i, 0, -1);
            }
        }

        if (three) return 3;
        else if (two) return 2;
        else if (one) return 1;
        else return 0;

    }
}
