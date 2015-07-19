package Tasks;

import javaUtils.BIT;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.ArrayList;

public class MercilessChef {
    final int INF = Integer.MAX_VALUE;
    int health[];
    ArrayList<Integer> adj[];
    int[] enter, exit;
    int tym = 0;

    void dfs(int v) {
        enter[v] = tym++;
        for (int u : adj[v]) {
            dfs(u);
        }
        exit[v] = tym;
    }

    void go(InReader in, OutputWriter out) {
        int n = in.readInt() + 1;

        health = new int[n];
        adj = new ArrayList[n];
        enter = new int[n];
        exit = new int[n];

        for (int i = 0; i < adj.length; i++) {
            adj[i] = new ArrayList<Integer>();

        }

        health[0] = INF;
        for (int i = 1; i < n; i++) {
            int h = in.readInt();
            int par = in.readInt();
            adj[par].add(i);
            health[i] = h;
        }

        dfs(0);

        //System.out.println(Arrays.toString(enter));
        //System.out.println(Arrays.toString(exit));

        BIT queryTree = new BIT(n + 1);

        int h[] = new int[n];
        for (int i = 0; i < n; i++) {
            h[enter[i]] = health[i];
        }

        SegTree tree = new SegTree(h);

        int q = in.readInt();
        while (q-- > 0) {
            int ch = in.readInt();
            switch (ch) {
                case 1:
                    int ind = in.readInt();
                    int sick = in.readInt();
                    int l = enter[ind] + 1;
                    int r = exit[ind] - 1;
                    if (r - l + 1 <= 0) break;
                    tree.update(l, r, sick);
                    do {
                        Node node = tree.query(l, r);
                        if (node.val <= 0) {
                            //System.out.println("node.ind = " + node.ind);
                            queryTree.update(node.ind, 1);
                            tree.delete(node.ind);
                        } else break;
                    } while (true);

                    break;

                case 2:
                    ind = in.readInt();
                    l = enter[ind] + 1;
                    r = exit[ind] - 1;
                    if (r - l + 1 <= 0) {
                        //System.out.println("leaf");
                        out.printLine(0);
                        break;
                    }
                    out.printLine(r - l + 1 - queryTree.rangeFreq(l, r));
                    break;

                default:
                    assert (false);
            }
        }


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

    class SegTree {
        int[] data;
        Node[] tree;
        int n;
        int[] flagged;

        SegTree(int[] data) {
            this.data = data;
            n = data.length;
            tree = new Node[4 * n];
            flagged = new int[4 * n];
            build(1, 0, n - 1);
        }

        Node combine(Node left, Node ryt) {
            Node ret = new Node();
            if (left.val < ryt.val) {
                ret.val = left.val;
                ret.ind = left.ind;
            } else {
                ret.val = ryt.val;
                ret.ind = ryt.ind;
            }
            return ret;
        }

        void build(int v, int tl, int tr) {
            if (tl == tr) {
                tree[v] = new Node(data[tl], tl);
                return;
            }
            int mid = tl + tr >> 1;
            build(v << 1, tl, mid);
            build(v << 1 | 1, mid + 1, tr);
            tree[v] = combine(tree[v << 1], tree[v << 1 | 1]);
        }

        void refresh(int v, int dec) {
            Node node = tree[v];
            node.val -= dec;
            flagged[v] += dec;
        }

        void push(int v) {
            if (flagged[v] > 0) {
                if (v << 1 >= 4 * n) return;           //we're on a leaf
                refresh(v << 1, flagged[v]);
                refresh(v << 1 | 1, flagged[v]);
                flagged[v] = 0;
            }
        }

        void update(int v, int tl, int tr, int l, int r, int dec) {
            if (tl >= l && tr <= r) {
                refresh(v, dec);
                return;
            }
            push(v);
            int mid = tl + tr >> 1;
            if (r <= mid) update(v << 1, tl, mid, l, r, dec);
            else if (l > mid) update(v << 1 | 1, mid + 1, tr, l, r, dec);
            else {
                update(v << 1, tl, mid, l, mid, dec);
                update(v << 1 | 1, mid + 1, tr, mid + 1, r, dec);
            }
            tree[v] = combine(tree[v << 1], tree[v << 1 | 1]);
        }

        void update(int l, int r, int dec) {
            update(1, 0, n - 1, l, r, dec);
        }

        Node query(int v, int tl, int tr, int l, int r) {
            if (tl == l && tr == r) {
                return tree[v];
            }
            push(v);
            int mid = tl + tr >> 1;
            if (r <= mid) return query(v << 1, tl, mid, l, r);
            else if (l > mid) return query(v << 1 | 1, mid + 1, tr, l, r);
            else {
                return combine(query(v << 1, tl, mid, l, mid), query(v << 1 | 1, mid + 1, tr, mid + 1, r));
            }
        }

        Node query(int l, int r) {
            return query(1, 0, n - 1, l, r);
        }

        void delete(int ind) {
            delete(1, 0, n - 1, ind);
        }

        private void delete(int v, int tl, int tr, int x) {
            if (tl == tr) {
                tree[v].val = INF;

                return;
            }
            push(v);
            int mid = tl + tr >> 1;
            if (x <= mid) delete(v << 1, tl, mid, x);
            else delete(v << 1 | 1, mid + 1, tr, x);
            tree[v] = combine(tree[v << 1], tree[v << 1 | 1]);
        }


    }

    class Node {
        int val;
        int ind;

        Node(int val, int ind) {
            this.val = val;
            this.ind = ind;
        }

        Node() {

        }
    }
}
