package Tasks;

import javaUtils.InReader;
import javaUtils.IntegerUtils;
import javaUtils.OutputWriter;

public class CountKPrimes {
    int[] primes;
    int[][] cnt;

    boolean go(long prod, int left, int k, int i) {
        if (prod >= 100005 && left > 0) return false;
        if (left == 0 && prod < 100005) {
            cnt[k][(int) prod] = 1;
            //System.out.println("prod = " + prod + " and k = " + k);
            return true;
        }
        for (int j = i; j < primes.length; j++) {
            if (prod * primes[j] >= 100005 || !go(prod * primes[j], left - 1, k, j + 1)) break;
            else {
                long res = prod * primes[j];
                while (res * primes[j] < 100005) {
                    res *= primes[j];
                    go(res, left - 1, k, j + 1);
                }
            }
        }
        return true;
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        cnt = new int[6][100005];
        primes = IntegerUtils.generatePrimes(100005);
        for (int i = 1; i < 6; i++) {
            go(1, i, i, 0);
            //System.out.println("done " + i);
        }
        for (int i = 1; i < 6; i++) {
            for (int j = 1; j < 100005; j++) {
                cnt[i][j] = cnt[i][j - 1] + cnt[i][j];
            }
        }
        testNumber = in.readInt();
        while (testNumber-- > 0) {
            int a = in.readInt();
            int b = in.readInt();
            int k = in.readInt();
            out.printLine(cnt[k][b] - cnt[k][a - 1]);
        }
    }
}
