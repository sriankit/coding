package Tasks;

import javaUtils.InReader;
import javaUtils.MaxMatchingEdmonds;
import javaUtils.OutputWriter;

import java.util.ArrayList;
import java.util.List;

public class SerejaAndGraph {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int m = in.readInt();
        List<Integer>[] g = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int u = in.readInt();
            int v = in.readInt();
            --u;
            --v;
            g[u].add(v);
            g[v].add(u);
        }
        out.printLine(2 * MaxMatchingEdmonds.maxMatching(g) != n ? "NO" : "YES");
    }
}
