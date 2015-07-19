package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.Arrays;

public class SubsetComponent {
    int n;
    long[] d;
    boolean[] vis;
    boolean[][] adj;

    int go(int mask) {
        boolean[] ones = new boolean[64];
        for (long i = 0; i < 64; i++) {
            for (int j = 0; j < n; j++) {
                long bit = 1 << i;
                System.out.println("bit = " + bit);
                if ((mask & (1 << j)) != 0 && (d[j] & bit) != 0) ones[(int) i] = true;
            }
        }

        System.out.println(Arrays.toString(ones));

        vis = new boolean[64];
        adj = new boolean[64][64];
        for (int i = 0; i < 64; i++) {
            for (int j = 0; j < 64; j++) {
                if (i != j && ones[i] && ones[j]) adj[i][j] = true;
            }
        }


        int cnt = 0;
        for (int i = 0; i < 64; i++) {
            if (!vis[i]) {
                dfs(i);
                cnt++;
            }
        }
        return cnt;
    }

    private void dfs(int u) {
        vis[u] = true;
        for (int i = 0; i < 64; i++) {
            if (i != u && adj[u][i] && !vis[i]) {
                dfs(i);
                System.out.println("i = " + i);
            }
        }
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        n = in.readInt();
        d = new long[n];
        for (int i = 0; i < n; i++) {
            d[i] = in.readLong();
        }

        int sum = 0;

        for (int i = 0; i < (1 << n); i++) {
            int add = go(i);
            System.out.println(i + "  >>  " + add);
            sum += add;
        }

        out.printLine(sum);

    }
}
