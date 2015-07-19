package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.ArrayList;

public class ProblemSolving {
    ArrayList<Integer>[] adjList;
    int[] done;
    boolean[] vis;

    boolean go(int i) {
        for (int j : adjList[i]) {
            if (!vis[j]) {
                vis[j] = true;
                if (done[j] == -1 || go(done[j])) {
                    done[j] = i;
                    return true;
                }
            }
        }
        return false;
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int k = in.readInt();
        int[] arr = IOUtils.readIntArray(in, n);
        adjList = new ArrayList[n];
        done = new int[n];
        for (int i = 0; i < n; i++) {
            done[i] = -1;
            adjList[i] = new ArrayList<Integer>();
            for (int j = i + 1; j < n; j++) {
                if (Math.abs(arr[i] - arr[j]) >= k) adjList[i].add(j);
            }
        }
        int ans = n;
        for (int i = 0; i < n; i++) {
            vis = new boolean[n];
            if (go(i)) ans--;
        }
        out.printLine(ans);
    }
}
