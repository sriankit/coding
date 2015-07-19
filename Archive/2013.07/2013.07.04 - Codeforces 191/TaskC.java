package Tasks;

import javaUtils.InReader;
import javaUtils.IntegerUtils;
import javaUtils.OutputWriter;

public class TaskC {
    long MOD = 1000000007;

    public void solve(int testNumber, InReader in, OutputWriter out) {
        String s = in.readString();
        int n = s.length();
        int k = in.readInt();
        long ans = 0;
        long r = IntegerUtils.power(2, n, MOD);
        long mult = IntegerUtils.power(r, k, MOD) - 1;
        if (mult < 0) mult += MOD;
        long inv = IntegerUtils.reverse(r - 1 + MOD, MOD);
        mult = (mult * inv) % MOD;
        for (int i = n - 1; i >= 0; i--) {
            if (s.charAt(i) == '5' || s.charAt(i) == '0') {
                ans += (IntegerUtils.power(2, i, MOD) * mult) % MOD;
                if (ans >= MOD) ans %= MOD;
            }
        }
        out.printLine(ans);
    }
}
