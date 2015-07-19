package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.*;

public class SEABAL {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int m = in.readInt();
        int[] arr = IOUtils.readIntArray(in, n);
        long[] pre = new long[n];
        pre[0] = arr[0];
        for (int i = 1; i < n; i++) {
            pre[i] = pre[i - 1] + arr[i];
        }
        int last = 0;
        Data[] pairs = new Data[m];
        for (int i = 0; i < m; i++) {
            int l = in.readInt() - 1;
            int r = in.readInt() - 1;
            long c = pre[r] - ((l - 1) >= 0 ? pre[l - 1] : 0);
            if (c == 0) last++;
            pairs[i] = new Data(l, r, c, 1);
        }
        SegTree tree = new SegTree(pairs, n);
        int k = in.readInt();
        while (k-- > 0) {
            int ind = in.readInt() + last - 1;
            int ans = tree.get(ind);
            out.printLine(ans + last);
            last += ans;
        }
    }

    class SegTree {
        Data[] arr;
        Node[] tree;
        //hash array for compressing r values
        int[] hash;
        int[] reHash;
        int n;
        ArrayList ar[];
        int pairCount;

        SegTree(Data[] arr, int maxR) {
            this.arr = arr;
            pairCount = arr.length;
            Arrays.sort(arr, new Comparator<Data>() {
                @Override
                public int compare(Data o1, Data o2) {
                    return o1.r - o2.r;
                }
            });

            hash = new int[maxR];
            reHash = new int[pairCount];

            int hashIdx = 0;
            hash[arr[0].r] = hashIdx;
            reHash[hashIdx] = arr[0].r;
            hashIdx++;

            for (int i = 1; i < arr.length; i++) {
                if (arr[i].r == arr[i - 1].r) continue;
                hash[arr[i].r] = hashIdx;
                reHash[hashIdx] = arr[i].r;
                hashIdx++;
            }

            this.n = hashIdx;

            ar = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                ar[i] = new ArrayList<Data>();
            }

            for (int i = 0; i < arr.length; i++)
                ar[hash[arr[i].r]].add(arr[i]);

            tree = new Node[4 * n];
            build(1, 0, n - 1);
        }

        Node combine(Node left, Node ryt) {
            Node ret = new Node(left.sz + ryt.sz);
            int i = 0;
            int j = 0;
            int res = 0;
            while (i < left.sz && j < ryt.sz) {
                if (left.ls[i] <= ryt.ls[j]) {
                    ret.ls[res] = left.ls[i];
                    ret.data[res] = left.data[i];
                    ret.mul[res] = left.mul[i];
                    i++;
                    res++;
                } else {
                    ret.ls[res] = ryt.ls[j];
                    ret.data[res] = ryt.data[j];
                    ret.mul[res] = ryt.mul[j];
                    j++;
                    res++;
                }
            }
            while (i < left.sz) {
                ret.ls[res] = left.ls[i];
                ret.data[res] = left.data[i];
                ret.mul[res] = left.mul[i];
                i++;
                res++;
            }
            while (j < ryt.sz) {
                ret.ls[res] = ryt.ls[j];
                ret.data[res] = ryt.data[j];
                ret.mul[res] = ryt.mul[j];
                j++;
                res++;
            }
            ret.init();
            return ret;
        }

        void build(int v, int tl, int tr) {
            if (tl == tr) {
                tree[v] = new Node(ar[tl]);
                tree[v].init();
                //System.out.println(tree[v].toString());
                //System.out.println(Arrays.toString(tree[v].mul));
                return;
            }
            int mid = tl + tr >> 1;
            build(v << 1, tl, mid);
            build(v << 1 | 1, mid + 1, tr);
            tree[v] = combine(tree[v << 1], tree[v << 1 | 1]);
            tree[v].init();
            //System.out.println(tree[v].toString());
            //System.out.println(Arrays.toString(tree[v].mul));
        }

        int get(int ind) {
            return query(1, 0, n - 1, ind);
        }

        int findVal(int v, int ind) {
            int qInd = bSearch(tree[v].ls, ind);
            if (qInd >= 0) {
                return tree[v].zeroCountingDS.query(qInd);
            } else return 0;
        }

        int query(int v, int tl, int tr, int ind) {
            if (tl == tr) {
                return findVal(v, ind);
            }
            int mid = tl + tr >> 1;
            if (reHash[mid] >= ind) {
                int ret = findVal(v << 1 | 1, ind);
                ret += query(v << 1, tl, mid, ind);
                return ret;
            } else {
                int ret = 0;
                //System.out.println("saved val = " + ret);
                ret += query(v << 1 | 1, mid + 1, tr, ind);
                return ret;
            }
        }

        private int bSearch(int[] ls, int ind) {
            int lo = 0, hi = ls.length - 1;
            if (ind > ls[ls.length - 1]) return hi;
            if (ind < ls[0]) return -1;
            int ans = -1;
            while (lo <= hi) {
                int mid = lo + hi >> 1;
                if (ls[mid] <= ind) {
                    lo = mid + 1;
                    ans = mid;
                } else {
                    hi = mid - 1;
                }
            }
            //System.out.println("returning " + ans + " for " + Arrays.toString(ls) + " ind = " + ind);
            return ans;
        }

        class Node {
            int[] ls;
            ZeroCountingDS zeroCountingDS;
            int[] mul;
            long[] data;
            int sz;

            Node(ArrayList<Data> arrayList) {
                TreeSet set = new TreeSet<Data>(new Comparator<Data>() {
                    @Override
                    public int compare(Data o1, Data o2) {
                        return o1.l - o2.l;
                    }
                });
                set.addAll(arrayList);
                Collections.sort(arrayList, new Comparator<Data>() {
                    @Override
                    public int compare(Data o1, Data o2) {
                        return o1.l - o2.l;
                    }
                });
                sz = set.size();
                ls = new int[sz];
                mul = new int[sz];
                data = new long[sz];
                int ptr = 0;
                for (int i = 0; i < arrayList.size(); i++) {
                    Data a = arrayList.get(i);
                    if (i == 0) {
                        ls[ptr] = a.l;
                        data[ptr] = a.c;
                        mul[ptr] = a.cnt;
                        continue;
                    }
                    if (a.l == arrayList.get(i - 1).l) {
                        mul[ptr]++;
                        continue;
                    }
                    ptr++;
                    ls[ptr] = a.l;
                    data[ptr] = a.c;
                    mul[ptr] = a.cnt;
                }
            }

            Node(int sz) {
                this.sz = sz;
                ls = new int[sz];
                mul = new int[sz];
                data = new long[sz];
            }

            void init() {
                zeroCountingDS = new ZeroCountingDS(data, mul);
            }

            @Override
            public String toString() {
                return Arrays.toString(ls);
            }
        }
    }


    class ZeroCountingDS {
        //An RMQ data structure, we check if the minimum value is zero, if so we count all occurrences of it
        //A difference sequence to actually count the number of zeros;

        long[] data;
        int n;
        int[] flagged;
        long[] tree;
        long[] diff;
        int[] retVal;
        int[] mul;
        int last = 0;

        ZeroCountingDS(long[] data, int[] mul) {
            this.data = data.clone();
            n = data.length;
            tree = new long[4 * n];
            flagged = new int[4 * n];
            this.mul = mul;
            retVal = new int[n];
            //setUpDiff();
            //getActualZeroCount();
            //build(1, 0, n - 1);
        }

        void getActualZeroCount() {
            last = retVal[retVal.length - 1];
            int sum = 0;
            int ret = 0;
            for (int i = 0; i < n; i++) {
                sum += diff[i];
                if (sum == 0) ret += mul[i];
                retVal[i] = ret;
            }
        }

        void update(int ind) {
            diff[0]--;
            if (ind + 1 < diff.length) diff[ind + 1]++;
            update(1, 0, n - 1, 0, ind);
        }

        int query(int ind) {
            update(ind);
            long min = query(1, 0, n - 1, 0, ind);
            if (min == 0) {
                getActualZeroCount();
            }
            return retVal[retVal.length - 1] - last;
        }

        void refresh(int v, int k) {
            tree[v] -= k;
            flagged[v] += k;
        }

        private void setUpDiff() {
            diff = new long[n];
            diff[0] = data[0];
            for (int i = 1; i < n; i++) {
                diff[i] = data[i] - data[i - 1];
            }
        }

        void build(int v, int tl, int tr) {
            if (tl == tr) {
                tree[v] = data[tl];
                return;
            }
            int mid = tl + tr >> 1;
            build(v << 1, tl, mid);
            build(v << 1 | 1, mid + 1, tr);
            tree[v] = Math.min(tree[v << 1], tree[v << 1 | 1]);
        }

        void push(int v) {
            if (flagged[v] > 0) {
                if (v << 1 >= 4 * n) return;           //we're on a leaf
                refresh(v << 1, flagged[v]);
                refresh(v << 1 | 1, flagged[v]);
                flagged[v] = 0;
            }
        }

        void update(int v, int tl, int tr, int l, int r) {
            if (tl >= l && tr <= r) {
                refresh(v, 1);
                return;
            }
            push(v);
            int mid = tl + tr >> 1;
            if (r <= mid) update(v << 1, tl, mid, l, r);
            else if (l > mid) update(v << 1 | 1, mid + 1, tr, l, r);
            else {
                update(v << 1, tl, mid, l, mid);
                update(v << 1 | 1, mid + 1, tr, mid + 1, r);
            }
            tree[v] = Math.min(tree[v << 1], tree[v << 1 | 1]);
        }

        long query(int v, int tl, int tr, int l, int r) {
            if (tl == l && tr == r) {
                return tree[v];
            }
            push(v);
            int mid = tl + tr >> 1;
            if (r <= mid) return query(v << 1, tl, mid, l, r);
            else if (l > mid) return query(v << 1 | 1, mid + 1, tr, l, r);
            else {
                return Math.min(query(v << 1, tl, mid, l, mid), query(v << 1 | 1, mid + 1, tr, mid + 1, r));
            }
        }

    }

}

class Data {
    int l, r, cnt;
    long c;

    Data(int l, int r, long c, int cnt) {
        this.l = l;
        this.r = r;
        this.c = c;
        this.cnt = cnt;
    }
}
