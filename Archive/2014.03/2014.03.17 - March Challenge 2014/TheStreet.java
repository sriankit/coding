package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.Arrays;

public class TheStreet {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int m = in.readInt();
        SegTreeLazy treeSum = new SegTreeLazy(n);
        SegTreeMax treeMax = new SegTreeMax(n);
        while (m-- > 0) {
            int ch = in.readInt();
            if (ch == 1) {
                int l = in.readInt() - 1;
                int r = in.readInt() - 1;
                int a = in.readInt();
                int b = in.readInt();
                treeMax.update(1, 0, n - 1, l, r, new Parabola(a, b));
            } else if (ch == 3) {
                int i = in.readInt() - 1;
                long res = treeMax.query(1, 0, n - 1, i, i);
                if (res == -10000000000L) out.printLine("NA");
                else out.printLine(res + treeSum.query(1, 0, n - 1, i, i));
            } else {
                int l = in.readInt() - 1;
                int r = in.readInt() - 1;
                int a = in.readInt();
                int b = in.readInt();
                treeSum.update(1, 0, n - 1, l, r, new Parabola(a, b));
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
                Parabola add = new Parabola(0, flagged[v].a * (mid - l + 1));
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
                Parabola add = new Parabola(0, par.a * (mid - l + 1));
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

    class SegTreeMax {
        long tree[];
        Parabola flagged[];
        int n;

        SegTreeMax(int n) {
            this.n = n;
            tree = new long[4 * n];
            Arrays.fill(tree, -10000000000L);
            flagged = new Parabola[tree.length];
        }

        long combine(long left, long ryt) {
            long ret = Math.max(left, ryt);
            return ret;
        }

        void refresh(int v, Parabola par, int i, int j) {
            //System.out.println("refreshing " + v);
            tree[v] = Math.max(tree[v], par.c);
            if (flagged[v] == null) {
                flagged[v] = new Parabola(par.a, par.c);
            } else {
                Parabola parc = (Parabola) par.clone();
                int br = flagged[v].adjust(parc, i, j);
                if (br != -1) {
                    update(v, i, j, i, br, flagged[v]);
                    parc.add(new Parabola(0, parc.a * (br - i + 1)));
                    update(v, i, j, br + 1, j, parc);
                    flagged[v] = null;
                }
            }
        }

        void push(int v, int l, int r) {
            if (flagged[v] != null) {
                //System.out.println("pushing  v = " + v);
                if (v << 1 >= 4 * n) return;           //we're on a leaf
                //System.out.println("pushing " + v);
                int mid = l + r >> 1;
                refresh(v << 1, flagged[v], l, mid);
                Parabola add = new Parabola(0, flagged[v].a * (mid - l + 1));
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
                Parabola add = new Parabola(0, par.a * (mid - l + 1));
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
            long n = j - i + 1;
            sum += a * ((n * (n - 1)) / 2);
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

        int adjust(Parabola p, int lo, int hi) {
            long vl1 = c;
            long vh1 = a * (hi - lo) + c;
            long vl2 = p.c;
            long vh2 = p.a * (hi - lo) + p.c;
            if (vl1 >= vl2 && vh1 >= vh2) return -1;
            if (vl2 >= vl1 && vh2 >= vh1) {
                this.a = p.a;
                this.c = p.c;
                return -1;
            }
            if (vl2 > vl1) {
                a = p.a + (p.a = a) * 0;
                c = p.c + (p.c = c) * 0;
            }
            int low = lo, high = hi;

            int br = lo;
            while (low <= high) {
                int mid = low + high >> 1;
                long v1 = a * (mid - lo) + c;
                long v2 = p.a * (mid - lo) + p.c;
                if (v1 >= v2) {
                    low = mid + 1;
                    br = mid;
                } else {
                    high = mid - 1;
                }
            }
            return br;
        }

        protected Object clone() {
            return new Parabola(a, c);
        }
    }
}
