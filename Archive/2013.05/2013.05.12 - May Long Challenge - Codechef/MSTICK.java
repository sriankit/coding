package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

public class MSTICK {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = in.readInt();
        }
        SegTree tree = new SegTree(arr);

        int q = in.readInt();
        while (q-- > 0) {
            int l = in.readInt();
            int r = in.readInt();
            int m1 = 0, m2 = tree.query(0, 1, 0, n - 1, l, r);
            if (l > 0) m1 = tree.query(1, 1, 0, n - 1, 0, l - 1);
            if (r < n - 1) m1 = Math.max(tree.query(1, 1, 0, n - 1, r + 1, n - 1), m1);
            out.printf("%.1f\n", m2 / 1.0 + Math.max(m1, (tree.query(1, 1, 0, n - 1, l, r) - m2) / 2.0));
        }
    }

    class SegTree {
        node tree[][];
        private int[] data;

        SegTree(int[] data) {
            this.data = data.clone();
            tree = new node[2][];
            tree[0] = new node[4 * data.length];
            tree[1] = new node[4 * data.length];
            build(1, 0, data.length - 1, 0);
            build(1, 0, data.length - 1, 1);
        }

        void build(int v, int tl, int tr, int mode) {
            if (tl == tr) {
                tree[mode][v] = new node(data[tl]);
                return;
            }
            int mid = (tl + tr) >> 1;
            build(v << 1, tl, mid, mode);
            build(v << 1 | 1, mid + 1, tr, mode);
            tree[mode][v] = combine(mode, v << 1, v << 1 | 1);
        }

        private node combine(int mode, int left, int ryt) {
            if (mode == 1) return new node(Math.max(tree[1][left].val, tree[1][ryt].val));
            else return new node(Math.min(tree[0][left].val, tree[0][ryt].val));
        }

        int query(int mode, int v, int tl, int tr, int l, int r) {
            if (tl == l && tr == r) {
                return tree[mode][v].val;
            }
            int mid = tl + tr >> 1;
            if (r <= mid) return query(mode, v << 1, tl, mid, l, r);
            else if (l > mid) return query(mode, v << 1 | 1, mid + 1, tr, l, r);
            else {
                int n1 = query(mode, v << 1, tl, mid, l, mid);
                int n2 = query(mode, v << 1 | 1, mid + 1, tr, mid + 1, r);
                if (mode == 1) return Math.max(n1, n2);
                else return Math.min(n1, n2);
            }
        }

    }

    class node {
        int val;

        node(int val) {
            this.val = val;
        }
    }

}
