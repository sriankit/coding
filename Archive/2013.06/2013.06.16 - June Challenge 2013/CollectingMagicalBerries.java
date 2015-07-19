package Tasks;


import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.IntegerUtils;
import javaUtils.OutputWriter;

public class CollectingMagicalBerries {
    final long MOD = 3046201;

    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int[] arr = IOUtils.readIntArray(in, n);
        long[] fact = IntegerUtils.generateFactorial(3000006, MOD);
        //long[] invFact = IntegerUtils.generateReverseFactorials(3000006, MOD);
        SegTree tree = new SegTree(arr);
        int q = in.readInt();
        while (q-- > 0) {
            String s = in.readString();
            int l = in.readInt() - 1;
            int r = in.readInt();
            if (s.equals("change")) tree.update(1, 0, n - 1, l, r);
            else {
                --r;
                int sum = tree.query(1, 0, n - 1, l, r);
                //System.out.println("sum = " + sum);
                int num = r - l + 1;
                long ans = fact[sum];
                if (false) {
                    ans = (ans * IntegerUtils.reverse(IntegerUtils.power(fact[sum / num], num, MOD), MOD)) % MOD;
                    out.printLine(ans);
                } else {
                    int div = sum / num;
                    int b1 = div + 1;
                    int b2 = div;
                    int n1 = sum % num;
                    int n2 = num - n1;
                    /*System.out.println("b1 = " + b1);
                    System.out.println("b2 = " + b2);
                    System.out.println("n1 = " + n1);
                    System.out.println("n2 = " + n2);  */
                    ans = (ans * IntegerUtils.reverse(IntegerUtils.power(fact[b1], n1, MOD), MOD)) % MOD;
                    ans = (ans * IntegerUtils.reverse(IntegerUtils.power(fact[b2], n2, MOD), MOD)) % MOD;
                    ans = (ans * fact[num]) % MOD;
                    ans = (ans * IntegerUtils.reverse(fact[n1], MOD)) % MOD;
                    ans = (ans * IntegerUtils.reverse(fact[n2], MOD)) % MOD;
                    out.printLine(ans);
                }

            }
        }
    }
}

class SegTree {
    int[] data;
    int n;
    int[] tree;

    SegTree(int[] data) {
        this.data = data;
        n = data.length;
        tree = new int[4 * n];
        build(1, 0, n - 1);
    }

    void build(int v, int tl, int tr) {
        if (tl == tr) {
            tree[v] = data[tl];
            return;
        }
        int mid = tl + tr >> 1;
        build(v << 1, tl, mid);
        build(v << 1 | 1, mid + 1, tr);
        tree[v] = tree[v << 1] + tree[v << 1 | 1];
    }

    void update(int v, int tl, int tr, int ind, int val) {
        if (tl == tr) {
            tree[v] = val;
            return;
        }
        int mid = tl + tr >> 1;
        if (ind > mid) update(v << 1 | 1, mid + 1, tr, ind, val);
        else update(v << 1, tl, mid, ind, val);
        tree[v] = tree[v << 1] + tree[v << 1 | 1];
    }

    int query(int v, int tl, int tr, int l, int r) {
        if (tl == l && tr == r) {
            return tree[v];
        }
        int mid = tl + tr >> 1;
        if (l > mid) return query(v << 1 | 1, mid + 1, tr, l, r);
        else if (r <= mid) return query(v << 1, tl, mid, l, r);
        else return (query(v << 1, tl, mid, l, mid) + query(v << 1 | 1, mid + 1, tr, mid + 1, r));
    }

}