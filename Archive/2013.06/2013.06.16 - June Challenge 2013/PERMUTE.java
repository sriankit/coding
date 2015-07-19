package Tasks;

import javaUtils.InReader;
import javaUtils.IntegerUtils;
import javaUtils.OutputWriter;

public class PERMUTE {
    final long MOD = 1000000007;

    public void solve(int testNumber, InReader in, OutputWriter out) {
        testNumber = in.readInt();
        long[] fact = IntegerUtils.generateFactorial(1000006, MOD);
        while (testNumber-- > 0) {
            int n = in.readInt();
            int m = in.readInt();
            int d = m - n;
            if (d == n - 1) out.printLine(fact[n]);
            else {
                d++;
                long ans = fact[d];
                int diff = n - d;
                ans = (ans * IntegerUtils.power(d, diff / 2, MOD)) % MOD;
                ans = (ans * IntegerUtils.power(d - 1, diff - diff / 2, MOD)) % MOD;
                out.printLine(ans);
            }
        }
    }
}
