package Tasks;

import javaUtils.InReader;
import javaUtils.IntegerUtils;
import javaUtils.OutputWriter;

public class BinaryTournament {
    int lim = 1 << 20;
    long MOD = 1000000009;
    long[] facs = IntegerUtils.generateFactorial(lim + 1, MOD);
    long[] inv = IntegerUtils.generateReverseFactorials(lim + 1, MOD);

    long getNCR(int n, int r) {
        if (n < r) return 0;
        return (facs[n] * ((inv[r] * inv[n - r]) % MOD)) % MOD;
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        int k = in.readInt();
        int n = 1 << k;
        int half = n >> 1;
        long mul = (facs[half] * facs[half]) % MOD;
        for (int i = 1; i <= n; i++) {
            int less = i - 1;
            long ans = getNCR(less, half - 1) * mul;
            if (ans >= MOD) ans %= MOD;
            ans *= 2;
            if (ans >= MOD) ans %= MOD;
            out.printLine(ans);
        }
    }
}
