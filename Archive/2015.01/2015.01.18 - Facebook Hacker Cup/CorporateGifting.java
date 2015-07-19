package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.ArrayList;

public class CorporateGifting {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        Thread t = new Thread(null, () -> goT(testNumber, in, out),"oh yeah",1<<24);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void goT(int testNumber, final InReader in, final OutputWriter out) {
        int n = in.readInt();
        dp = new int[n][3];
        adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < n; i++) {
            int p = in.readInt();
            if(p > 0) {
                --p;
                adj[p].add(i);
            }
        }
        int ans = Math.min(go(0, 3), Math.min(go(0, 1), go(0, 2)));
        out.printf("Case #%d: %d\n", testNumber, ans);
    }

    private int go(int v, int col) {
        if(dp[v][col] != -1) return dp[v][col];
        int cost = col;
        for(int child : adj[v]) {
            if(col == 1) cost += Math.min(go(child, 2), go(child, 3));
            if(col == 2) cost += Math.min(go(child, 1), go(child, 3));
            if(col == 3) cost += Math.min(go(child, 1), go(child, 2));
        }
        return dp[v][col] = cost;
    }

    ArrayList<Integer> adj[];
    int dp[][];
}
