package Tasks;

import javaUtils.CollectionUtils;
import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.ArrayList;
import java.util.Collections;

public class MaximumNumberGCDCondition {
    int lim = 100005;
    int[] primes = new int[10000];
    ArrayList<Integer> inds[];
    SegTree[] trees;

    public void solve(int testNumber, InReader in, OutputWriter out) {
        ArrayList<Integer> facs[] = new ArrayList[lim];
        for (int i = 0; i < lim; i++) {
            facs[i] = new ArrayList<Integer>();
        }
        for (int i = 2; i < lim; i++) {
            if (facs[i].size() == 0) {
                //primes[pc++] = i;
                for (int j = i; j < lim; j += i) {
                    facs[j].add(i);
                }
            }
        }
        int n, q;
        n = in.readInt();
        q = in.readInt();
        int[] a = IOUtils.readIntArray(in, n);
        trees = new SegTree[lim];
        ArrayList<Integer> data[] = new ArrayList[lim];
        inds = new ArrayList[lim];
        for (int i = 0; i < lim; i++) {
            data[i] = new ArrayList<Integer>();
            inds[i] = new ArrayList<Integer>();
        }
        for (int i1 = 0; i1 < a.length; i1++) {
            int num = a[i1];
            for (int fac : facs[num]) {
                data[fac].add(num);
                inds[fac].add(i1);
            }
        }
        for (int i = 0; i < lim; i++) {
            trees[i] = new SegTree(CollectionUtils.toArray(data[i]));
        }
        //System.out.println(pc);
        while (q-- > 0) {
            int g = in.readInt();
            int x = in.readInt() - 1;
            int y = in.readInt() - 1;
            int ans = -1, occ = -1;
            for (int fac : facs[g]) {
                Node res = getNode(fac, x, y);
                if (res.max != -1) {
                    if (res.max > ans) {
                        ans = res.max;
                        occ = res.occ;
                    }
                }
            }
            out.printLine(ans + " " + occ);
        }

    }

    Node getNode(int pr, int x, int y) {
        if (inds[pr].size() == 0) return new Node(-1, -1);
        int l = Collections.binarySearch(inds[pr], x);
        if (l < 0) l = -l - 1;
        int r = Collections.binarySearch(inds[pr], y);
        if (r < 0) r = -r - 1 - 1;
        //System.out.println(pr + " " + x + " " + y + " " + l + " " + r + "  [" + inds[pr] + " ]");
        return trees[pr].query(1, 0, inds[pr].size() - 1, l, r);
    }

    class SegTree {
        int[] data;
        Node[] tree;
        int n;

        SegTree(int[] data) {
            this.data = data;
            n = data.length;
            tree = new Node[4 * n];
            if (n > 0) build(1, 0, n - 1);
        }

        Node combine(Node lft, Node ryt) {
            if (lft.max == ryt.max) return new Node(lft.max, lft.occ + ryt.occ);
            else if (lft.max > ryt.max) return new Node(lft.max, lft.occ);
            else return new Node(ryt.max, ryt.occ);
        }

        void build(int v, int tl, int tr) {
            if (tl == tr) {
                tree[v] = new Node(data[tl], 1);
                return;
            }
            int mid = tl + tr >> 1;
            build(v << 1, tl, mid);
            build(v << 1 | 1, mid + 1, tr);
            tree[v] = combine(tree[v << 1], tree[v << 1 | 1]);
        }

        Node query(int v, int tl, int tr, int l, int r) {
            if (tl < 0 || tr < 0 || l < 0 || r < 0 || l > r || l >= n || r >= n || n == 0) return new Node(-1, -1);
            //System.out.println(tl + " " + tr + " " + v);
            if (tl == l && tr == r) {
                return tree[v];
            }
            int mid = tl + tr >> 1;
            if (r <= mid) return query(v << 1, tl, mid, l, r);
            else if (l > mid) return query(v << 1 | 1, mid + 1, tr, l, r);
            else return combine(query(v << 1, tl, mid, l, mid), query(v << 1 | 1, mid + 1, tr, mid + 1, r));
        }


    }

    class Node {
        int max, occ;

        Node(int max, int occ) {
            this.max = max;
            this.occ = occ;
        }
    }
}
