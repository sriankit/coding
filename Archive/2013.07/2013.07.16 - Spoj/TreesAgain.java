package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.ArrayList;
import java.util.Arrays;

public class TreesAgain {
    ArrayList<Integer> adj[];
    int k;
    long[][] height;
    boolean[] visited;
    long[][] dp;

    void setHeights(int u, int par) {
        height[u][1] = 1;
        for (int v : adj[u]) {
            if (v == par) continue;
            setHeights(v, u);
            for (int i = 1; i <= k; i++) {
                height[u][i] += height[v][i - 1];
            }
        }
    }

    void makeDP(int u, int par) {
        if (adj[u].size() == 1 && par != -1) dp[u][1] = 1;
        else {
            for (int v : adj[u]) {
                if (v == par) continue;
                makeDP(v, u);
            }
            dp[u][1] = 1;
            for (int max = 2; max < k + 1; max++) {
                long calc = 1;
                boolean take = false;
                for (int v : adj[u]) {
                    if (v == par) continue;
                    long sum = 0;
                    if (dp[v][max - 1] > 0) take = true;
                    for (int i = 0; i < max; i++) {
                        sum += dp[v][i];
                    }
                    calc *= sum;
                }
                if (take) dp[u][max] = calc;
            }
        }
    }

    long go(int u, int par) {
        if (adj[u].size() == 1 && par != -1) return 1;
        long ans = 0;
        int childCnt = 0;
        int[] children = new int[adj[u].size()];
        System.out.println("for " + u);
        for (int v : adj[u]) {
            if (v != par) {
                children[childCnt++] = v;
                ans += go(v, u);
            }
        }

        for (int i = 0; i < k + 1; i++) {
            //this will be value for 0th child
            long prod = height[children[0]][i];
            for (int j = 1; j < childCnt; j++) {
                long sum = 0;
                for (int l = 0; l <= (k - i); l++) {
                    sum += height[children[j]][l];
                }
                prod *= sum;
            }
            ans += prod;
        }
        System.out.println("returned " + ans + " for " + u);
        return ans;
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        k = in.readInt();
        adj = new ArrayList[n];
        dp = new long[n][k + 1];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < n - 1; i++) {
            int u = in.readInt();
            int v = in.readInt();
            adj[u].add(v);
            adj[v].add(u);
        }
        height = new long[n][k + 1];
        setHeights(0, -1);
        for (int i = 0; i < n; i++) {
            height[i][0] = 1;
        }
        makeDP(0, -1);
        System.out.println(Arrays.deepToString(height));
        System.out.println(Arrays.deepToString(dp));
        out.printLine(go(0, -1));
    }
}
