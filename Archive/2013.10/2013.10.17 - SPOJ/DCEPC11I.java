package Tasks;


import javaUtils.InReader;
import javaUtils.OutputWriter;

public class DCEPC11I {

    long[] sums;

    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int q = in.readInt();
        sums = new long[n];
        sums[0] = 0;
        for (int i = 1; i < n; i++) {
            sums[i] = sums[i - 1] + i;
        }
        SegTreeLazy tree = new SegTreeLazy(n);
        while (q-- > 0) {
            int ch = in.readInt();
            int l = in.readInt() - 1;
            int r = in.readInt() - 1;
            if (ch == 1) {
                out.printLine(tree.query(1, 0, n - 1, l, r));
            } else {
                Parabola p = new Parabola(1, 0);
                tree.update(1, 0, n - 1, l, r, p);
            }
        }
    }

    class SegTreeLazy {
        long tree[];
        Parabola flagged[];
        int n;

        SegTreeLazy(int n) {
            this.n = n;
            tree = new long[4 * n];
            flagged = new Parabola[tree.length];
        }

        long combine(long left, long ryt) {
            long ret = left + ryt;
            return ret;
        }

        void refresh(int v, Parabola par, int i, int j) {
            //System.out.println("refreshing " + v);
            tree[v] += par.getSum(i, j);
            if (flagged[v] == null) {
                flagged[v] = new Parabola(par.a, par.c);
            } else flagged[v].add(par);
        }

        void push(int v, int l, int r) {
            if (flagged[v] != null) {
                //System.out.println("pushing  v = " + v);
                if (v << 1 >= 4 * n) return;           //we're on a leaf
                //System.out.println("pushing " + v);
                int mid = l + r >> 1;
                refresh(v << 1, flagged[v], l, mid);
                Parabola add = new Parabola(0, flagged[v].a * (mid + 1 - l));
                flagged[v].add(add);
                refresh(v << 1 | 1, flagged[v], mid + 1, r);
                flagged[v] = null;
            }
        }

        void update(int v, int tl, int tr, int l, int r, Parabola par) {
            if (tl >= l && tr <= r) {
                refresh(v, par, tl, tr);
                return;
            }
            push(v, tl, tr);
            int mid = tl + tr >> 1;
            if (r <= mid) update(v << 1, tl, mid, l, r, par);
            else if (l > mid) update(v << 1 | 1, mid + 1, tr, l, r, par);
            else {
                update(v << 1, tl, mid, l, mid, par);
                Parabola add = new Parabola(0, par.a * (mid + 1 - l));
                par.add(add);
                update(v << 1 | 1, mid + 1, tr, mid + 1, r, par);
                //update(v << 1 | 1, mid + 1, tr, mid + 1, r, add);
                par.del(add);
            }
            tree[v] = combine(tree[v << 1], tree[v << 1 | 1]);
        }

        long query(int v, int tl, int tr, int l, int r) {
            if (tl == l && tr == r) {
                return tree[v];
            }
            //System.out.printf("%d %d %d %d\n", tl, tr, l, r);
            push(v, tl, tr);
            int mid = tl + tr >> 1;
            if (r <= mid) return query(v << 1, tl, mid, l, r);
            else if (l > mid) return query(v << 1 | 1, mid + 1, tr, l, r);
            else {
                return combine(query(v << 1, tl, mid, l, mid), query(v << 1 | 1, mid + 1, tr, mid + 1, r));
            }
        }
    }

    class MaxParabola extends Parabola {
        MaxParabola(long a, long c) {
            super(a, c);
        }

        @Override
        void add(Parabola p) {

        }
    }

    class Parabola {
        long a, c;

        Parabola(long a, long c) {
            this.a = a;
            this.c = c;
        }

        long getSum(int i, int j) {
            //System.out.println("i = " + i + " j = " + j);
            long sum = c * (j - i + 1);
            int n = j - i + 1;
            sum += a * ((n * (n + 1)) >> 1);
            //System.out.println("returned " + sum);
            return sum;
        }

        void add(Parabola p) {
            this.a += p.a;
            this.c += p.c;
        }

        void del(Parabola p) {
            this.a -= p.a;
            this.c -= p.c;
        }
    }

}
