package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.ArrayList;

public class EvenTree {
    int n, m;
    ArrayList<Integer> adj[];
    boolean[] visited;

    public void solve(int testNumber, InReader in, OutputWriter out) {
        n = in.readInt();
        m = in.readInt();
        adj = new ArrayList[n];
        visited = new boolean[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int u = in.readInt() - 1;
            int v = in.readInt() - 1;
            adj[u].add(v);
            adj[v].add(u);
        }
        int[] ans = dfs(0);
        out.printLine(ans[0] > 0 ? ans[0] - 1 : 0);
    }

    private int[] dfs(int v) {
        int valids = 0;
        int childCount = 1;
        visited[v] = true;
        for (int u : adj[v]) {
            if (visited[u]) continue;
            int[] ret = dfs(u);
            valids += ret[0];
            childCount += ret[1];
        }
        if (childCount % 2 == 0) valids++;
        return new int[]{valids, childCount};
    }
}
