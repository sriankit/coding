package Tasks;

import javaUtils.BIT;
import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.ArrayList;
import java.util.Stack;

public class TywinsTactics {
    int[] skills;
    int[] order;
    int[] childCount;
    int done = 0;
    int[] hash;
    ArrayList<Integer>[] adj;
    long[] nSkil;
    int n;

    int preorder(int v, int par) {
        int id = done;
        order[done++] = v;
        int ret = 1;
        for (int u : adj[v]) {
            if (u != par) ret += preorder(u, v);
        }
        return childCount[id] = ret;
    }

    void go() {
        Stack<Info> stack = new Stack<Info>();
        stack.push(new Info(-1, 0));
        boolean[] vis = new boolean[n];
        boolean[] allDone = new boolean[n];
        vis[0] = true;
        order[done++] = 0;
        //System.out.println("pushed 0");
        while (!stack.empty()) {
            Info top = stack.peek();
            int node = (top.parent == -1) ? 0 : adj[top.parent].get(top.idx);
            if (adj[node].size() == 1 || allDone[node]) {
                stack.pop();
                if (top.parent == -1) return;
                childCount[top.parent] += childCount[node];
                int nxt = top.idx + 1;
                if (nxt < adj[top.parent].size()) {
                    if (vis[adj[top.parent].get(nxt)])
                        nxt++;
                    if (nxt < adj[top.parent].size()) {
                        vis[adj[top.parent].get(nxt)] = true;
                        //System.out.println("pushed " + adj[top.parent].get(nxt));
                        order[done++] = adj[top.parent].get(nxt);
                        stack.push(new Info(top.parent, nxt));
                    } else allDone[top.parent] = true;
                } else allDone[top.parent] = true;
            } else {
                int id = 0;
                if (vis[adj[node].get(0)]) id = 1;
                vis[adj[node].get(id)] = true;
                //System.out.println("pushed " + adj[node].get(id));
                order[done++] = adj[node].get(id);
                stack.push(new Info(node, id));
            }
        }
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        n = in.readInt();
        int q = in.readInt();
        skills = IOUtils.readIntArray(in, n);
        nSkil = new long[n];
        hash = new int[n];
        order = new int[n];
        childCount = new int[n];

        adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < n - 1; i++) {
            int u = in.readInt() - 1;
            int v = in.readInt() - 1;
            adj[u].add(v);
            adj[v].add(u);
        }

        for (int i = 0; i < n; i++) {
            childCount[i] = 1;
        }

        go();

        for (int i = 0; i < n; i++) {
            hash[order[i]] = i;
        }
        //System.out.println(Arrays.toString(hash));

        for (int i = 0; i < n; i++) {
            nSkil[hash[i]] = skills[i];
        }

        BIT tree = new BIT(n + 1);

        for (int i = 1; i <= n; i++) {
            tree.update(i, nSkil[i - 1]);
        }

        /*System.out.println(Arrays.toString(nSkil));
        System.out.println(Arrays.toString(childCount));
        for (int i = 1; i <= n; i++) {
            System.out.println(tree.freqAt(i));
        } */
        while (q-- > 0) {
            char c = in.readCharacter();
            int s = hash[in.readInt() - 1];
            //System.out.println("s = " + s);
            if (c == 'U') {
                long val = in.readInt();
                tree.update(s + 1, val - nSkil[s]);
                nSkil[s] = val;
            } else {
                out.printLine(tree.freqTo(s + childCount[s]) - tree.freqTo(s));
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
