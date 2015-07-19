package Tasks;

import javaUtils.BIT;
import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class SerejaAndBallons {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int m = in.readInt();
        int[] arr;
        arr = IOUtils.readIntArray(in, n);
        long[] pre = new long[n];
        pre[0] = arr[0];
        for (int i = 1; i < n; i++) {
            pre[i] = pre[i - 1] + arr[i];
        }

        data[] pairs = new data[m];
        for (int i = 0; i < m; i++) {
            int l = in.readInt() - 1;
            int r = in.readInt() - 1;
            long c = pre[r] - ((l - 1) >= 0 ? pre[l - 1] : 0);
            pairs[i] = new data(l, r, c);
        }

        SegTree tree = new SegTree(pairs, n);
    }

    class data {
        int l;
        int r;
        long c;

        data(int l, int r, long c) {
            this.l = l;
            this.r = r;
            this.c = c;
        }
    }

    class SegTree {
        data[] arr;
        Node[] tree;
        int[] hash;
        int[] reHash;
        int n;
        ArrayList<data>[] ar;

        SegTree(data[] datas, int arrLen) {
            this.arr = datas;
            hash = new int[arrLen];
            reHash = new int[arrLen];
            Arrays.sort(datas, new Comparator<data>() {
                @Override
                public int compare(data o1, data o2) {
                    if (o1.r == o2.r) return o1.l - o2.l;
                    else return o1.r - o2.r;
                }
            });
            int rCount = 0;
            int j = 0;
            hash[datas[0].r] = j;
            reHash[j++] = datas[0].r;

            for (int i = 1; i < datas.length; i++) {
                if (datas[i].r == datas[i - 1].r) continue;
                hash[datas[i].r] = j;
                reHash[j++] = datas[i].r;
            }

            this.n = j;

            ar = new ArrayList[this.n];
            for (int i = 0; i < this.n; i++) {
                ar[i] = new ArrayList<data>();
            }
            ar[0].add(datas[0]);
            int k = 0;
            for (int i = 1; i < datas.length; i++) {
                if (datas[i].r == datas[i - 1].r) {
                    ar[k].add(datas[i]);
                } else {
                    k++;
                    ar[k].add(datas[i]);
                }
            }

            tree = new Node[4 * this.n];
            build(1, 0, this.n - 1);
        }

        Node combine(Node l, Node r) {
            Node ret = new Node(l.sz + r.sz);
            int res = 0;
            int i = 0;
            int j = 0;
            while (res < ret.sz) {
                if (i >= l.sz || j >= r.sz) break;
                if (l.l[i].l < r.l[j].l) ret.l[res++] = l.l[i++];
                else ret.l[res++] = r.l[j++];
            }

            while (i < l.sz) ret.l[res++] = l.l[i++];
            while (j < r.sz) ret.l[res++] = r.l[j++];

            ret.preSum[0] = ret.l[0].c;
            for (int k = 1; k < ret.sz; k++) {
                ret.preSum[i] = ret.preSum[i - 1] + ret.l[i].c;
            }

            return ret;
        }

        void build(int v, int tl, int tr) {
            if (tl == tr) {
                tree[v] = new Node(ar[tl].size());
                tree[v].l[0] = ar[tl].get(0);
                tree[v].preSum[0] = tree[v].l[0].c;
                for (int i = 1; i < ar[tl].size(); i++) {
                    tree[v].l[i] = ar[tl].get(i);
                    tree[v].preSum[i] = tree[v].preSum[i - 1] + tree[v].l[i].c;
                }
                return;
            }
            int mid = tl + tr >> 1;
            build(v << 1, tl, mid);
            build(v << 1 | 1, mid + 1, tr);
            tree[v] = combine(tree[v << 1], tree[v << 1 | 1]);
        }

        void push(int v) {
            Node node = tree[v];
            if (node.ops.size() > 0) {
                if (v << 1 >= 4 * this.n) {
                    node.ops.clear();
                    return;
                }
                tree[v << 1].ops.addAll(node.ops);
                tree[v << 1 | 1].ops.addAll(node.ops);
                node.ops.clear();
            }
        }


    }

    class Node {
        ArrayList<Integer> ops;
        int sz;
        data[] l;
        BIT sumBIT;
        BIT cBIT;
        long preSum[];

        Node(int sz) {
            ops = new ArrayList<Integer>();
            this.sz = sz;
            sumBIT = new BIT(sz + 1);
            cBIT = new BIT(sz + 1);
            l = new data[sz];
            preSum = new long[sz];
        }
    }
}
