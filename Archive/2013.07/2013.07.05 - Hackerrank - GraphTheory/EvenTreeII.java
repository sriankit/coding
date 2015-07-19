package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.ArrayList;
import java.util.Stack;

public class EvenTreeII {
    ArrayList<Integer> adj[];
    int size[];
    int n;

    public void solve(int testNumber, InReader in, OutputWriter out) {
        n = in.readInt();
        int m = in.readInt();
        size = new int[n];
        adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            size[i] = 1;
        }
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < n - 1; i++) {
            int u = in.readInt() - 1;
            int v = in.readInt() - 1;
            adj[v].add(u);
            adj[u].add(v);
        }
        go();
        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (size[i] % 2 == 0) ans++;
        }
        //System.out.println(Arrays.toString(size));
        out.printLine(ans > 0 ? ans - 1 : 0);
    }

    void go() {
        Stack<Info> stack = new Stack<Info>();
        stack.push(new Info(-1, 0));
        boolean[] vis = new boolean[n];
        boolean[] allDone = new boolean[n];
        vis[0] = true;
        //System.out.println("pushed 0");
        while (!stack.empty()) {
            Info top = stack.peek();
            int node = (top.parent == -1) ? 0 : adj[top.parent].get(top.idx);
            if (adj[node].size() == 1 || allDone[node]) {
                stack.pop();
                if (top.parent == -1) return;
                size[top.parent] += size[node];
                int nxt = top.idx + 1;
                if (nxt < adj[top.parent].size()) {
                    if (vis[adj[top.parent].get(nxt)])
                        nxt++;
                    if (nxt < adj[top.parent].size()) {
                        vis[adj[top.parent].get(nxt)] = true;
                        //System.out.println("pushed " + adj[top.parent].get(nxt));
                        stack.push(new Info(top.parent, nxt));
                    } else allDone[top.parent] = true;
                } else allDone[top.parent] = true;
            } else {
                int id = 0;
                if (vis[adj[node].get(0)]) id = 1;
                vis[adj[node].get(id)] = true;
                //System.out.println("pushed " + adj[node].get(id));
                stack.push(new Info(node, id));
            }
        }
    }

    class Info {
        int parent, idx;

        Info(int parent, int idx) {
            this.idx = idx;
            this.parent = parent;
        }
    }
}
