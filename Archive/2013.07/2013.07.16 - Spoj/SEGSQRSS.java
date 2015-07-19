package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

public class SEGSQRSS {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        out.printLine("Case " + testNumber + ":");
        int n, q;
        n = in.readInt();
        q = in.readInt();
        int[] arr = IOUtils.readIntArray(in, n);
        SegTreeLazy tree = new SegTreeLazy(arr);
        while (q-- > 0) {
            int ch, l, r;
            ch = in.readInt();
            l = in.readInt() - 1;
            r = in.readInt() - 1;
            switch (ch) {
                case 0:
                    tree.update(1, 0, n - 1, l, r, in.readInt(), 2);
                    break;
                case 1:
                    tree.update(1, 0, n - 1, l, r, in.readInt(), 1);
                    break;
                case 2:
                    out.printLine(tree.query(1, 0, n - 1, l, r).sqSum);
                    break;
                default:
                    throw new RuntimeException("Invalid input");
            }
        }
    }
}

class SegTreeLazy {
    int data[];
    Node tree[];
    byte flagged[];
    long vals[];
    int n;

    SegTreeLazy(int[] data) {
        this.data = data;
        n = data.length;
        tree = new Node[4 * n];
        flagged = new byte[tree.length];
        vals = new long[tree.length];
        build(1, 0, n - 1);
    }

    Node combine(Node left, Node ryt) {
        Node ret = new Node();
        ret.sqSum = left.sqSum + ryt.sqSum;
        ret.sum = left.sum + ryt.sum;
        ret.sz = left.sz + ryt.sz;
        return ret;
    }

    void build(int v, int tl, int tr) {
        if (tl == tr) {
            tree[v] = new Node(data[tl]);
            return;
        }
        int mid = tl + tr >> 1;
        build(v << 1, tl, mid);
        build(v << 1 | 1, mid + 1, tr);
        tree[v] = combine(tree[v << 1], tree[v << 1 | 1]);
    }

    void refresh(int v, long val, int type) {
        Node node = tree[v];
        assert (node.sz > 0);
        if (type == 2) { //set update
            node.sum = node.sz * val;
            node.sqSum = node.sz * (val * val);
            vals[v] = val;
            flagged[v] = 2;
        } else if (type == 1) { //inc update
            if (flagged[v] == 2) {
                assert (node.sum % node.sz == 0);
                refresh(v, vals[v] + val, 2);
            } else {
                node.sqSum += (val * val) * node.sz + 2 * val * node.sum;
                node.sum += val * node.sz;
                flagged[v] = 1;
                vals[v] = val;
            }
        }
    }

    void push(int v) {
        if (flagged[v] > 0) {
            flagged[v] = 0;
            if (v << 1 >= 4 * n) return;           //we're on a leaf
            refresh(v << 1, vals[v], flagged[v]);
            refresh(v << 1 | 1, vals[v], flagged[v]);
        }
    }

    void update(int v, int tl, int tr, int l, int r, int val, int type) {
        if (tl >= l && tr <= r) {
            refresh(v, val, type);
            return;
        }
        push(v);
        int mid = tl + tr >> 1;
        if (r <= mid) update(v << 1, tl, mid, l, r, val, type);
        else if (l > mid) update(v << 1 | 1, mid + 1, tr, l, r, val, type);
        else {
            update(v << 1, tl, mid, l, mid, val, type);
            update(v << 1 | 1, mid + 1, tr, mid + 1, r, val, type);
        }
        tree[v] = combine(tree[v << 1], tree[v << 1 | 1]);
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

    class Node {
        long sqSum;
        long sum;
        int sz;

        Node(int x) {
            sum = x;
            sqSum = x * x;
            sz = 1;
        }

        Node() {
        }
    }
}
