package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

public class RainingParabolas {
    final int MOD = 10007;
    long sqSums[], sums[];

    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int q = in.readInt();
        sqSums = new long[n];
        sums = new long[n];
        sqSums[0] = 0;
        sums[0] = 0;
        for (int i = 1; i < n; i++) {
            sums[i] = sums[i - 1] + i;
            sqSums[i] = sqSums[i - 1] + ((long) i) * i;
            if (sums[i] >= MOD) sums[i] %= MOD;
            if (sqSums[i] >= MOD) sqSums[i] %= MOD;
        }
        SegTreeLazy tree = new SegTreeLazy(n);
        while (q-- > 0) {
            int ch = in.readInt();
            int l = in.readInt();
            int r = in.readInt();
            if (ch == 1) {
                out.printLine(tree.query(1, 0, n - 1, l, r));
            } else {
                Parabola p = new Parabola(in.readInt(), in.readInt(), in.readInt());
                tree.update(1, 0, n - 1, l, r, p);
            }
        }
    }


    class SegTreeLazy {
        int tree[];
        Parabola flagged[];
        int n;

        SegTreeLazy(int n) {
            this.n = n;
            tree = new int[4 * n];
            flagged = new Parabola[tree.length];
        }

        int combine(int left, int ryt) {
            int ret = left + ryt;
            if (ret >= MOD) ret -= MOD;
            return ret;
        }

        void refresh(int v, Parabola par, int i, int j) {
            //System.out.println("refreshing " + v);
            tree[v] += par.getSum(i, j);
            if (tree[v] >= MOD) tree[v] %= MOD;
            if (flagged[v] == null) {
                flagged[v] = new Parabola(par.a, par.b, par.c);
            } else flagged[v].add(par);
        }

        void push(int v, int l, int r) {
            if (flagged[v] != null) {
                if (v << 1 >= 4 * n) return;           //we're on a leaf
                //System.out.println("pushing " + v);
                int mid = l + r >> 1;
                refresh(v << 1, flagged[v], l, mid);
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
                update(v << 1 | 1, mid + 1, tr, mid + 1, r, par);
            }
            tree[v] = combine(tree[v << 1], tree[v << 1 | 1]);
        }

        int query(int v, int tl, int tr, int l, int r) {
            if (tl == l && tr == r) {
                return tree[v];
            }
            push(v, tl, tr);
            int mid = tl + tr >> 1;
            if (r <= mid) return query(v << 1, tl, mid, l, r);
            else if (l > mid) return query(v << 1 | 1, mid + 1, tr, l, r);
            else {
                return combine(query(v << 1, tl, mid, l, mid), query(v << 1 | 1, mid + 1, tr, mid + 1, r));
            }
        }
    }

    class Parabola {
        int a, b, c;

        Parabola(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        long getSum(int i, int j) {
            long sum = c * (j - i + 1);
            if (sum >= MOD) sum %= MOD;

            long sumN = sums[j];
            if (i > 0) sumN -= sums[i - 1];
            if (sumN < 0) sumN += MOD;

            sum += b * sumN;
            if (sum >= MOD) sum %= MOD;

            sumN = sqSums[j];
            if (i > 0) sumN -= sqSums[i - 1];
            if (sumN < 0) sumN += MOD;

            sum += a * sumN;
            if (sum >= MOD) sum %= MOD;
            //System.out.println("returned " + sum);
            return sum;
        }

        void add(Parabola p) {
            this.a += p.a;
            this.b += p.b;
            this.c += p.c;
            if (a >= MOD) a -= MOD;
            if (b >= MOD) b -= MOD;
            if (c >= MOD) c -= MOD;
        }
    }
}
