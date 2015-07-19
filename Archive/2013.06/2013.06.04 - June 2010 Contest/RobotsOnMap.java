package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RobotsOnMap {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        testNumber = in.readInt();
        while (testNumber-- > 0) {
            //System.out.println("new");
            int m = in.readInt();
            Trie trie = new Trie(m);
            for (int i = 0; i < m; i++) {
                int n = in.readInt();
                int child[][] = new int[n + 1][2];
                for (int j = 0; j < n - 1; j++) {
                    int a = in.readInt();
                    int c = 0;
                    if (in.readCharacter() == 'R') c = 1;
                    int b = in.readInt();
                    child[a][c] = b;
                }
                dfs(1, trie, child, "", i);
            }
            int[] ans = new int[m + 1];
            trie.calc(ans);
            for (int i = m - 1; i > 0; i--) {
                if (ans[i] < ans[i + 1]) ans[i] = ans[i + 1];
            }
            IOUtils.writeIntArray(out, ans, 1);
        }
    }

    private void dfs(int i, Trie trie, int[][] child, String s, int mn) {
        if (child[i][0] == 0 && child[i][1] == 0) {
            trie.insert(s, mn);
            //System.out.println(s);
        }
        if (child[i][0] != 0) dfs(child[i][0], trie, child, s + '0', mn);
        if (child[i][1] != 0) dfs(child[i][1], trie, child, s + '1', mn);
    }
}

class Trie {
    final Node root = new Node(-1, 0, 0);
    int m;

    Trie(int m) {
        this.m = m;
    }

    void insert(String s, int mn) {
        Node node = root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int num = (c == '0') ? 0 : 1;
            if (node.children[num] == null) node.children[num] = new Node(num, i + 1, m);
            node = node.children[num];
            if (!node.done[mn]) {
                node.done[mn] = true;
                node.cnt++;
            }
        }
    }

    void calc(int[] a) {
        Node node = root;
        Queue<Node> q = new ConcurrentLinkedQueue<Node>();
        if (node.children[0] != null) q.add(node.children[0]);
        if (node.children[1] != null) q.add(node.children[1]);
        while (!q.isEmpty()) {
            node = q.poll();
            if (node.children[0] != null) q.add(node.children[0]);
            if (node.children[1] != null) q.add(node.children[1]);
            a[node.cnt] = Math.max(a[node.cnt], node.d);
        }
    }

    boolean isLeaf(Node node) {
        return node.children[0] == null && node.children[1] == null;
    }

    class Node {
        Node children[];
        int c;
        int cnt;
        int d;
        boolean done[];

        Node(int c, int d, int m) {
            this.c = c;
            children = new Node[2];
            cnt = 0;
            this.d = d;
            done = new boolean[m];
        }


    }
}