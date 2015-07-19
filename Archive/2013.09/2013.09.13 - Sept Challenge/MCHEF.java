package Tasks;


import javaUtils.BIT;
import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.ArrayList;

public class MCHEF {
    int[] skills;
    int[] order;
    int[] childCount;
    int done = 0;
    int[] hash;
    ArrayList<Integer>[] adj;
    int[] nSkil;

    int preorder(int v, int par) {
        int id = done;
        order[done++] = v;
        int ret = 1;
        for (int u : adj[v]) {
            if (u != par) ret += preorder(u, v);
        }
        return childCount[id] = ret;
    }

    public void solve(int testNumber, final InReader in, final OutputWriter out) {
        Thread t = new Thread(null, new Runnable() {
            @Override
            public void run() {
                go(in, out);
            }
        }, "oh yeah", 1 << 24);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void go(InReader in, OutputWriter out) {
        int n = in.readInt();
        int q = in.readInt();
        skills = IOUtils.readIntArray(in, n);
        nSkil = new int[n];
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

        preorder(0, -1);
        for (int i = 0; i < n; i++) {
            hash[order[i]] = i;
        }

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
                int val = in.readInt();
                tree.update(s + 1, val - nSkil[s]);
                nSkil[s] = val;
            } else {
                out.printLine(tree.freqTo(s + childCount[s]) - tree.freqTo(s));
            }
        }
    }
}
