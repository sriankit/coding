package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.ArrayList;

public class Draughts {
    int through[];
    boolean vis[];
    int open[];
    ArrayList<Integer> adj[];

    int dfs(int u) {
        int ret = open[u];
        vis[u] = true;
        for (int v : adj[u]) {
            if (!vis[v]) ret += dfs(v);
        }
        if (ret != 0) through[u] = 1;
        return ret;
    }

    public void solve(InReader in, OutputWriter out) {
        int n = in.readInt();
        int m = in.readInt();
        vis = new boolean[n];
        through = new int[n];
        adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        open = IOUtils.readIntArray(in, n);
        for (int i = 0; i < m; i++) {
            int u = in.readInt() - 1;
            int v = in.readInt() - 1;
            adj[u].add(v);
            adj[v].add(u);
        }

        long pairs = 0;
        int th = 0;

        for (int i = 0; i < n; i++) {
            if (!vis[i] && open[i] == 1) {
                long op = dfs(i);
                if (op >= 2) {
                    pairs += op * (op - 1) / 2;
                } else if (op == 1) th--;
                assert (op > 0);
            }
        }

        for (int i = 0; i < n; i++) {
            if (through[i] == 1) th++;
        }
        out.printLine(pairs, th);
    }

    public void solve(int testNumber, final InReader in, final OutputWriter out) {
        Thread t = new Thread(null, new Runnable() {
            @Override
            public void run() {
                solve(in, out);
            }
        }, "oh yeah", 1 << 24);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
