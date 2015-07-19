package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.Multiset;
import javaUtils.OutputWriter;

import java.util.ArrayList;

public class LibraryQuery {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        testNumber = in.readInt();
        while (testNumber-- > 0) {
            int n = in.readInt();
            int[] a = IOUtils.readIntArray(in, n);
            SegTree segTree = new SegTree(a);
            int queryCount = in.readInt();
            while (queryCount-- > 0) {
                int ch = in.readInt();
                int x = in.readInt() - 1;
                if (ch == 1) {
                    int k = in.readInt();
                    segTree.update(1, 0, n - 1, x, k, a[x]);
                    a[x] = k;
                } else {
                    int y = in.readInt() - 1;
                    int k = in.readInt();
                    out.printLine(segTree.query(x, y, k));
                }
                ////System.out.println(Arrays.toString(a));
            }

        }

    }

    class SegTree {
        int[] data;
        Multiset[] tree;
        int n;
        ArrayList<Multiset> list;


        SegTree(int[] data) {
            this.data = data;
            n = data.length;
            tree = new Multiset[4 * n];
            build(1, 0, n - 1);
            //System.out.println("tree[1] " + tree[1].toString() + " here");
            list = new ArrayList<Multiset>();
        }

        void combine(Multiset par, Multiset left, Multiset ryt) {
            par.putAll(left);
            par.putAll(ryt);
        }

        void update(int v, int tl, int tr, int ind, int newVal, int oldVal) {
            tree[v].deleteOneInstance(oldVal);
            tree[v].add(newVal);
            if (tl == tr) {
                return;
            }
            int mid = tl + tr >> 1;
            if (ind <= mid) update(v << 1, tl, mid, ind, newVal, oldVal);
            else update(v << 1 | 1, mid + 1, tr, ind, newVal, oldVal);
        }

        void build(int v, int tl, int tr) {
            if (tl == tr) {
                tree[v] = new Multiset(1001);
                int num = data[tl];
                tree[v].add(num);
                //System.out.println(tl + " " + tr + " --- " + tree[v].toString());
                return;
            }
            int mid = tl + tr >> 1;
            build(v << 1, tl, mid);
            build(v << 1 | 1, mid + 1, tr);
            tree[v] = new Multiset(1001);
            tree[v].putAll(tree[v << 1]);
            tree[v].putAll(tree[v << 1 | 1]);
            //System.out.println(tl + " " + tr + " --- " + tree[v].toString());
        }

        int total(int x) {
            int val = 0;
            for (Multiset set : list) {
                val += set.getLesserCount(x);
            }
            return val;
        }

        int query(int l, int r, int k) {
            list = new ArrayList<Multiset>();
            query(1, 0, n - 1, l, r);
            //for (Multiset m : list) //System.out.println(m.toString());
            int lo = 1, hi = 1000;
            int ans = hi;
            while (lo <= hi) {
                int mid = lo + hi >> 1;
                if (total(mid) <= k - 1) {
                    ans = mid;
                    lo = mid + 1;
                } else hi = mid - 1;
            }
            return ans;
        }

        void query(int v, int tl, int tr, int l, int r) {
            if (tl == l && tr == r) {
                list.add(tree[v]);
                //System.out.println("added " + tl + " " + tr);
                return;
            }
            int mid = tl + tr >> 1;
            if (r <= mid) query(v << 1, tl, mid, l, r);
            else if (l > mid) query(v << 1 | 1, mid + 1, tr, l, r);
            else {
                query(v << 1, tl, mid, l, mid);
                query(v << 1 | 1, mid + 1, tr, mid + 1, r);
            }
        }

    }
}
