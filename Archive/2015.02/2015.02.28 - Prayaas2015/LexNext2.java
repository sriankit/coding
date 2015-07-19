package Tasks;



import Learning.SuffixTree;
import javaUtils.*;

public class LexNext2 {
    RMQ RMQlcp, RMQsa;
    int sa[], lcp[], sa_inv[];
    public void solve(int testNumber, InReader in, OutputWriter out) {
        String str = in.readString();
        int n = str.length();
        sa_inv = new int[n];
        sa = SuffixArray.suffixArray(str);
        lcp = new int[n];
        System.arraycopy(SuffixArray.lcp(sa, str), 0, lcp, 1, n - 1);
        //IOUtils.writeIntArray(out, sa, 0);
        //IOUtils.writeIntArray(out, lcp, 0);
        for (int i = 0; i < n; i++) {
            sa_inv[sa[i]] = i;
        }
        RMQlcp = new RMQ(lcp);
        RMQsa = new RMQ(sa);
        int queryCount = in.readInt();
        while (queryCount --> 0) {
            int idx = sa_inv[in.readInt() - 1];
            int len = in.readInt();
            if(idx == n - 1) {
                out.printLine(-1);
                continue;
            }
            int this_last = getLastIndex(idx, len, n);
            if(this_last == n - 1) out.printLine(-1);
            else {
                int next_first = getNextofValidLength(this_last + 1, len, n);
                if(next_first == -1) {
                    out.printLine(-1);
                    continue;
                }
                int next_last = getLastIndex(next_first, len, n);
                //System.err.println(idx + " " + next_first + " " + next_last + " " + RMQsa.getMin(next_first, next_last));
                out.printLine(RMQsa.getMin(next_first, next_last) + 1);
            }
        }
    }

    int getLastIndex(int idx, int len, int n) {
        if(idx == n - 1 || lcp[idx + 1] < len || lcp[idx + 1] == 0) return idx; //one and only occurence
        int lo = idx + 1, hi = n - 1, ans = idx;
        while(lo <= hi) {
            int mid = lo + hi >> 1;
            int rmin = RMQlcp.getMin(idx + 1, mid);
            if(rmin < len) hi = mid - 1;
            else {
                lo = mid + 1;
                ans = mid;
            }
        }
        return ans;
    }

    int getNextofValidLength(int idx, int len, int n) {
        //int i = idx;
        //while(i < size && size - sa[i] < len) i++;
        //if(i == size) return -1;
        int lo = idx, hi = n - 1;
        int ans = -1;
        while(lo <= hi) {
            int mid = lo + hi >> 1;
            int msa = RMQsa.getMin(idx, mid);
            if(msa <= n - len) {
                hi = mid - 1;
                ans = mid;
            }
            else
                lo = mid + 1;
        }
        return ans;
    }

    class RMQ {
        int[] data;
        int[] tree;
        int n;

        public RMQ(int[] data) {
            this.data = data;
            n = data.length;
            tree = new int[4 * n];
            build(1, 0, n - 1);
        }

        void build(int v, int tl, int tr) {
            if(tl == tr) tree[v] = data[tl];
            else {
                int mid = tl + tr >> 1;
                build(v << 1, tl, mid);
                build(v << 1 | 1, mid + 1, tr);
                tree[v] = Math.min(tree[v << 1], tree[v << 1 | 1]);
            }
        }

        int query(int v, int tl, int tr, int l, int r) {
            if(tl == l && tr == r) return tree[v];
            int mid = tl + tr >> 1;
            if(l > mid) return query(v << 1 | 1, mid + 1, tr, l, r);
            else if(r <= mid) return query(v << 1, tl, mid, l, r);
            else return Math.min(query(v << 1, tl, mid, l, mid), query(v << 1 | 1, mid + 1, tr, mid + 1, r));
        }

        int getMin(int i, int j) {
            return query(1, 0, n - 1, i, j);
        }

    }

    class SparseTable {

        int[] logTable;
        int[][] rmq;
        int[] a;

        public SparseTable(int[] a) {
            this.a = a;
            int n = a.length;

            logTable = new int[n + 1];
            for (int i = 2; i <= n; i++)
                logTable[i] = logTable[i >> 1] + 1;

            rmq = new int[logTable[n] + 1][n];

            for (int i = 0; i < n; ++i)
                rmq[0][i] = i;

            for (int k = 1; (1 << k) < n; ++k) {
                for (int i = 0; i + (1 << k) <= n; i++) {
                    int x = rmq[k - 1][i];
                    int y = rmq[k - 1][i + (1 << k - 1)];
                    rmq[k][i] = a[x] <= a[y] ? x : y;
                }
            }
        }

        int minPos(int i, int j) {
            int k = logTable[j - i];
            int x = rmq[k][i];
            int y = rmq[k][j - (1 << k) + 1];
            return a[x] <= a[y] ? x : y;
        }

        int getMin(int i, int j) {
            return a[minPos(i, j)];
        }
    }
}
