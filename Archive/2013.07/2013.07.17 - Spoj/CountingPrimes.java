package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.IntegerUtils;
import javaUtils.OutputWriter;

public class CountingPrimes {
    boolean[] isPrime;

    //TLE in java AC in cpp
    public void solve(int testNumber, InReader in, OutputWriter out) {
        testNumber = in.readInt();
        isPrime = IntegerUtils.generatePrimalityTable(1000006);
        for (int TEST = 1; TEST <= testNumber; TEST++) {
            out.printLine("Case " + TEST + ":");
            int n, q;
            n = in.readInt();
            q = in.readInt();
            int[] arr = IOUtils.readIntArray(in, n);
            SegTreeLazy tree = new SegTreeLazy(arr);
            while (q-- > 0) {
                int ch;
                ch = in.readInt();
                int l, r;
                l = in.readInt() - 1;
                r = in.readInt() - 1;

                if (ch == 0) tree.update(1, 0, n - 1, l, r, in.readInt());
                else if (ch == 1) out.printLine(tree.query(1, 0, n - 1, l, r).primeCount);
                else throw new RuntimeException("Invalid Input");
            }

        }
    }

    class SegTreeLazy {
        int data[];
        Node tree[];
        int flagged[];
        int n;

        SegTreeLazy(int[] data) {
            this.data = data;
            n = data.length;
            tree = new Node[4 * n];
            flagged = new int[tree.length];
            build(1, 0, n - 1);
        }

        Node combine(Node left, Node ryt) {
            Node ret = new Node();
            ret.primeCount = left.primeCount + ryt.primeCount;
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

        void refresh(int v, int val) {
            Node node = tree[v];
            if (isPrime[val]) node.primeCount = node.sz;
            else node.primeCount = 0;
            flagged[v] = val;
        }

        void push(int v) {
            if (flagged[v] > 0) {
                if (v << 1 >= 4 * n) return;           //we're on a leaf
                refresh(v << 1, flagged[v]);
                refresh(v << 1 | 1, flagged[v]);
                flagged[v] = 0;
            }
        }

        void update(int v, int tl, int tr, int l, int r, int val) {
            if (tl >= l && tr <= r) {
                refresh(v, val);
                return;
            }
            push(v);
            int mid = tl + tr >> 1;
            if (r <= mid) update(v << 1, tl, mid, l, r, val);
            else if (l > mid) update(v << 1 | 1, mid + 1, tr, l, r, val);
            else {
                update(v << 1, tl, mid, l, mid, val);
                update(v << 1 | 1, mid + 1, tr, mid + 1, r, val);
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
            int primeCount;
            int sz;

            Node(int num) {
                sz = 1;
                if (isPrime[num]) primeCount = 1;
                else primeCount = 0;
            }

            Node() {
            }
        }
    }
}
