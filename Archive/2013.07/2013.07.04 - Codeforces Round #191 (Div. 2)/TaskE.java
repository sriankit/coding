package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.IntegerUtils;
import javaUtils.OutputWriter;

import java.util.Arrays;

class TaskE {
    long[] facs;
    long MOD = 1000000007;
    int[] arr;
    int[] blocked;
    int n;
    int k;

    public void solve(int testNumber, InReader in, OutputWriter out) {
        facs = IntegerUtils.generateFactorial(30, MOD);
        //System.out.println(Arrays.toString(facs));
        n = in.readInt();
        arr = IOUtils.readIntArray(in, n);
        k = in.readInt();
        blocked = IOUtils.readIntArray(in, k);
        Arrays.sort(arr);
        Arrays.sort(blocked);
        long ans = facs[n];
        long sub = 0;
        for (int i = 1; i < 1 << k; i++) {
            int idx = getLowestBit(i);
            //System.out.println("idx = " + idx + " for i = " + i);
            long val = go(0, 0, 0, blocked[idx], i, 0);
            if (Integer.bitCount(i) % 2 == 0) sub -= val;
            else sub += val;
            if (sub < 0) sub += MOD;
            if (sub >= MOD) sub %= MOD;
            //System.out.println("done " + i);
        }
        ans -= sub;
        ans %= MOD;
        if (ans < 0) ans += MOD;
        out.printLine(ans);
    }

    private int getLowestBit(int i) {
        for (int j = 0; i > 0; j++, i >>= 1) {
            if ((i & 1) == 1) return j;
        }
        return -1;
    }

    long go(int ntaken, int ind, long sum, int blockval, int j, int mask) {
        if (ind == n) return 0;
        //System.out.println("called with " + ntaken + " " + ind + " " + sum + " " + j + " " + mask);
        long ret = 0;
        int idx = getLowestBit(j);
        //System.out.println(" idx = " + idx + " for j = " + j);
        int total = Integer.bitCount(mask);
        for (int i = ind; i < n; i++) {
            if ((1 << i & mask) == 0) {
                if (sum + arr[i] == blockval) {
                    long lret = facs[ntaken + 1];
                    j ^= 1 << idx;
                    //System.out.println("j = " + j);
                    if (j > 0) {
                        int nidx = getLowestBit(j);
                        lret = (lret * go(0, 0, 0, blocked[nidx] - blocked[idx], j, mask | 1 << i));
                    } else lret = (lret * facs[n - (total + 1)]) % MOD;
                    ret += lret;
                    //System.out.println("lret = " + lret + " for " + blocked[idx] + " mask = " + (mask | 1 << i));
                    if (ret >= MOD) ret %= MOD;
                } else {
                    ret += go(ntaken + 1, i + 1, sum + arr[i], blockval, j, mask | 1 << i);
                }
                j |= 1 << idx;
            }
        }
        return ret;
    }
}