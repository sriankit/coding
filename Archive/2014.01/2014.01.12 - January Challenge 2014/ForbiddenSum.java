package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.Arrays;

public class ForbiddenSum {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int data[] = IOUtils.readIntArray(in, n);
        SegTree tree = new SegTree(data);
        int queryCount = in.readInt();
        while (queryCount-- > 0) {
            int l = in.readInt() - 1;
            int r = in.readInt() - 1;
            Node res = tree.query(1, 0, n - 1, l, r);
            int s = 0;
            int ans = 1;
            boolean done = false;
            for (int bucket = 0; bucket < 32; bucket++) {
                if (res.min[bucket] != Integer.MAX_VALUE && res.min[bucket] > s + 1) {
                    ans = s + 1;
                    done = true;
                    break;
                }
                s += res.sum[bucket];
            }
            if (done) out.printLine(ans);
            else out.printLine(s + 1);
        }
    }

    class SegTree {

        int data[];
        Node tree[];
        int n;

        SegTree(int[] data) {
            this.data = data;
            n = data.length;
            tree = new Node[4 * n];
            build(1, 0, n - 1);
        }

        int findBucket(int n) {
            long lim = 1;
            int bucket = 0;
            while (lim < n) {
                bucket++;
                lim = 2 * (lim + 1) - 1;
            }
            return bucket;
        }

        void build(int v, int tl, int tr) {
            if (tl == tr) {
                tree[v] = new Node();
                int bucket = findBucket(data[tl]);
                tree[v].sum[bucket] += data[tl];
                tree[v].min[bucket] = Math.min(tree[v].min[bucket], data[tl]);
                return;
            }
            int mid = tl + tr >> 1;
            build(v << 1, tl, mid);
            build(v << 1 | 1, mid + 1, tr);
            tree[v] = combine(tree[v << 1], tree[v << 1 | 1]);
        }

        Node query(int v, int tl, int tr, int l, int r) {
            if (tl == l && tr == r) {
                return tree[v];
            }
            int mid = tl + tr >> 1;
            if (l > mid) return query(v << 1 | 1, mid + 1, tr, l, r);
            else if (r <= mid) return query(v << 1, tl, mid, l, r);
            else return combine(query(v << 1, tl, mid, l, mid), query(v << 1 | 1, mid + 1, tr, mid + 1, r));
        }

        Node combine(Node left, Node ryt) {
            Node res = new Node();
            for (int bucket = 0; bucket < 32; bucket++) {
                res.sum[bucket] += left.sum[bucket] + ryt.sum[bucket];
                res.min[bucket] = Math.min(left.min[bucket], ryt.min[bucket]);
            }
            return res;
        }
    }

    class Node {
        int[] sum, min;

        Node() {
            sum = new int[32];
            min = new int[32];
            Arrays.fill(min, Integer.MAX_VALUE);
        }
    }
}
