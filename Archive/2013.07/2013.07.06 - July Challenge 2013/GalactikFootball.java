package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.ArrayList;

public class GalactikFootball {
    ArrayList<Integer>[] adj;
    boolean visited[];
    long[] cost;

    long dfs(int v) {
        visited[v] = true;
        long ret = (cost[v] >= 0 ? cost[v] : -1);
        for (int u : adj[v]) {
            if (!visited[u]) {
                long val = dfs(u);
                if (val != -1) {
                    if (ret == -1 || val < ret) ret = val;
                }
            }
        }
        return ret;
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int m = in.readInt();
        adj = new ArrayList[n];
        visited = new boolean[n];
        cost = new long[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int a = in.readInt() - 1;
            int b = in.readInt() - 1;
            adj[a].add(b);
            adj[b].add(a);
        }

        for (int i = 0; i < n; i++) {
            int c = in.readInt();
            cost[i] = (c >= 0 ? c : -1);
        }
        int compCnt = 0;
        long compSum = 0;
        long minComp = Integer.MAX_VALUE;
        boolean punk = false;
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                long cmp = dfs(i);
                if (cmp < 0) {
                    punk = true;
                }
                compSum += cmp;
                minComp = Math.min(minComp, cmp);
                compCnt++;
            }
        }
        //System.out.println("punk = " + punk);
        if (punk) {
            if (compCnt > 1) out.printLine(-1);
            else out.printLine(0);
        } else out.printLine(compSum - minComp + (compCnt - 1) * minComp);
    }
}
