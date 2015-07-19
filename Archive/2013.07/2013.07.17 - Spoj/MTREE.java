package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.ArrayList;
import java.util.Iterator;

public class MTREE {
    int n;
    ArrayList<Integer> adj[];
    ArrayList<Integer> cst[];
    long[] dp;
    long MOD = 1000000007;

    long dfs(int u, int par) {
        long ret = 0;
        long res = 0;
        long mult = 0;
        Iterator nxt = adj[u].iterator();
        Iterator cost = cst[u].iterator();
        while (nxt.hasNext()) {
            int v = (Integer) nxt.next();
            int w = (Integer) cost.next();
            if (v == par) continue;

            long val = (1 + dfs(v, u)) * w;
            if (val >= MOD) val %= MOD;
            ret += val;
            if (ret >= MOD) ret -= MOD;

            res = (res + mult * val) % MOD;
            mult += val;
            if (mult >= MOD) mult -= MOD;
        }
        dp[u] = ret + res;
        if (dp[u] >= MOD) dp[u] -= MOD;

        return ret;
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        n = in.readInt();
        adj = new ArrayList[n];
        cst = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
            cst[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < n - 1; i++) {
            int u = in.readInt() - 1;
            int v = in.readInt() - 1;
            int c = in.readInt();
            adj[u].add(v);
            adj[v].add(u);
            cst[u].add(c);
            cst[v].add(c);
        }
        dp = new long[n];
        dfs(0, -1);
        //System.out.println(Arrays.toString(dp));
        long s = 0;
        for (int i = 0; i < n; i++) {
            s += dp[i];
            if (s >= MOD) s -= MOD;
        }
        out.printLine(s);
    }

}
