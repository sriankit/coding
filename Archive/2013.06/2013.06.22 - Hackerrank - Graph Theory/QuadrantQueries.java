package Tasks;

import javaUtils.ArrayUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

public class QuadrantQueries {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int[][] arr = new int[n][2];
        for (int i = 0; i < n; i++) {
            arr[i][0] = in.readInt();
            arr[i][1] = in.readInt();
        }
        SegTree tree = new SegTree(arr);
        int q = in.readInt();
        while (q-- > 0) {
            char c = in.readCharacter();
            int l = in.readInt() - 1;
            int r = in.readInt() - 1;
            switch (c) {
                case 'X':
                    tree.update(1, 0, n - 1, l, r, 0);
                    break;
                case 'Y':
                    tree.update(1, 0, n - 1, l, r, 1);
                    break;
                case 'C':
                    ArrayUtils.printArr(out, tree.query(1, 0, n - 1, l, r).q);
            }
        }
    }

    class SegTree {
        int[][] data;
        Node[] tree;
        boolean[] refX, refY;
        int n;

        SegTree(int[][] data) {
            this.data = data;
            n = data.length;
            tree = new Node[4 * n];
            refX = new boolean[tree.length];
            refY = new boolean[tree.length];
            build(1, 0, n - 1);
        }

        void refresh(int v, int xory) {
            Node node = tree[v];
            if (xory == 0) {
                node.q[0] = node.q[3] + (node.q[3] = node.q[0]) * 0;
                node.q[1] = node.q[2] + (node.q[2] = node.q[1]) * 0;
                refX[v] = !refX[v];
            } else {
                node.q[0] = node.q[1] + (node.q[1] = node.q[0]) * 0;
                node.q[2] = node.q[3] + (node.q[3] = node.q[2]) * 0;
                refY[v] = !refY[v];
            }
        }

        void push(int v) {
            if (refX[v]) {
                refX[v] = false;
                if (v << 1 >= 4 * n) return;           //we're on a leaf
                refresh(v << 1, 0);
                refresh(v << 1 | 1, 0);
            }
            if (refY[v]) {
                refY[v] = false;
                if (v << 1 >= 4 * n) return;           //we're on a leaf
                refresh(v << 1, 1);
                refresh(v << 1 | 1, 1);

            }
        }

        void update(int v, int tl, int tr, int l, int r, int xory) {
            if (tl >= l && tr <= r) {
                refresh(v, xory);
                return;
            }
            push(v);
            int mid = tl + tr >> 1;
            if (r <= mid) update(v << 1, tl, mid, l, r, xory);
            else if (l > mid) update(v << 1 | 1, mid + 1, tr, l, r, xory);
            else {
                update(v << 1, tl, mid, l, mid, xory);
                update(v << 1 | 1, mid + 1, tr, mid + 1, r, xory);
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


        Node combine(Node n1, Node n2) {
            Node ret = new Node();
            for (int i = 0; i < 4; i++) {
                ret.q[i] = n1.q[i] + n2.q[i];
            }
            return ret;
        }

        void build(int v, int tl, int tr) {
            if (tl == tr) {
                tree[v] = new Node(data[tl][0], data[tl][1]);
                return;
            }
            int mid = tl + tr >> 1;
            build(v << 1, tl, mid);
            build(v << 1 | 1, mid + 1, tr);
            tree[v] = combine(tree[v << 1], tree[v << 1 | 1]);
        }

        class Node {
            int[] q;

            Node() {
                q = new int[4];
            }

            Node(int x, int y) {
                q = new int[4];
                if (x > 0) {
                    if (y > 0) q[0] = 1;
                    else q[3] = 1;
                } else {
                    if (y > 0) q[1] = 1;
                    else q[2] = 1;
                }
            }
        }
    }
}
