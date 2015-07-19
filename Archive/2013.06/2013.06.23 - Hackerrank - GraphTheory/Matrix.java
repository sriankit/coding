package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;
import javaUtils.Pair;

import java.util.ArrayList;
import java.util.Iterator;

public class Matrix {
    int n, k;
    ArrayList<Integer> adjList[];
    ArrayList<Long> cost[];
    boolean label[];
    int[] labCount;
    boolean[] vis;
    Pair<Long, Long> dp[];

    int dfs(int v) {
        vis[v] = true;
        int rt = 0;
        for (int child : adjList[v])
            if (!vis[child]) rt += dfs(child);
        return labCount[v] = rt + (label[v] ? 1 : 0);
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        n = in.readInt();
        k = in.readInt();
        adjList = new ArrayList[n];
        cost = new ArrayList[n];
        label = new boolean[n];
        vis = new boolean[n];
        labCount = new int[n];
        dp = new Pair[n];

        for (int i = 0; i < n; i++) {
            adjList[i] = new ArrayList<Integer>();
            cost[i] = new ArrayList<Long>();
        }

        for (int i = 0; i < n - 1; i++) {
            int u = in.readInt();
            int v = in.readInt();
            long c = in.readInt();
            adjList[u].add(v);
            adjList[v].add(u);
            cost[u].add(c);
            cost[v].add(c);
        }
        for (int i = 0; i < k; i++) {
            label[in.readInt()] = true;
        }

        dfs(0);

        out.printLine(go(-1, 0, 1).second);
        //System.out.println(Arrays.deepToString(dp));

    }

    Pair<Long, Long> go(int par, int root, int detached) {
        //System.out.println("called for root = " + root + " with detached = " + detached);
        if (labCount[root] == 0) return Pair.makePair(0L, 0L);
        if (detached == 1 && labCount[root] == 1) return Pair.makePair(0L, 0L);
        if (dp[root] != null) return dp[root];
        boolean remove = !label[root];
        Iterator it = adjList[root].iterator();
        Iterator cit = cost[root].iterator();
        long ret = 0;
        long maxDetach = 0;
        while (it.hasNext()) {
            int child = (Integer) it.next();
            Long cost = (Long) cit.next();
            if (child == par) continue;
            if (labCount[child] == 0) continue;
            if (label[child]) {
                Pair<Long, Long> p = go(root, child, 1);
                maxDetach = Math.max(maxDetach, cost);
                ret += cost + p.second;
            } else {
                Pair<Long, Long> p = go(root, child, 1);
                Pair<Long, Long> p2 = go(root, child, 0);
                if (cost + p.second < p2.first + p2.second) {
                    maxDetach = Math.max(maxDetach, cost);
                    ret += cost + p.second;
                } else {
                    maxDetach = Math.max(maxDetach, p2.first);
                    ret += p2.first + p2.second;
                }
            }
        }
        if (remove) ret -= maxDetach;
        return dp[root] = Pair.makePair(maxDetach, ret);
    }
}