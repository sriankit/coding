package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.ArrayList;

public class PizzaCelebration {
    ArrayList<Integer> adj[];
    boolean[] visited;

    int dfs(int v) {
        visited[v] = true;
        int size = 1;
        for (int u : adj[v]) {
            if (!visited[u]) size += dfs(u);
        }
        return size;
    }

    public void solve(int testNumber, final InReader in, final OutputWriter out) {
        Thread t = new Thread(null, new Runnable() {
            @Override
            public void run() {
                solve(in, out);
            }
        }, "yes", 1 << 24);

        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void solve(InReader in, OutputWriter out) {
        int testNumber = in.readInt();
        while (testNumber-- > 0) {
            int vc = in.readInt();
            int edges = in.readInt();
            adj = new ArrayList[vc];
            for (int i = 0; i < vc; i++) {
                adj[i] = new ArrayList<Integer>();
            }
            visited = new boolean[vc];

            for (int i = 0; i < edges; i++) {
                int u = in.readInt() - 1;
                int v = in.readInt() - 1;
                adj[u].add(v);
                adj[v].add(u);
            }
            int ans = 0;
            int oddMx = -1;

            for (int i = 0; i < vc; i++) {
                if (!visited[i]) {
                    int cnt = dfs(i);
                    if (cnt % 2 == 0) ans += cnt;
                    else oddMx = Math.max(oddMx, cnt);
                }
            }
            out.printLine(ans + oddMx + 1);
        }
    }
}
