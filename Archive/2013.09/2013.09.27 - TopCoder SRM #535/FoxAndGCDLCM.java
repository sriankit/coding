package Tasks;

import javaUtils.IntegerUtils;

public class FoxAndGCDLCM {
    public long get(long G, long L) {
        if (L % G != 0) return -1;
        long P = L / G;
        long ans = Long.MAX_VALUE;
        for (int i = 1; i <= P / i; i++) {
            if (P % i == 0 && IntegerUtils.gcd(i, P / i) == 1) ans = Math.min(ans, i + P / i);
        }
        if (ans == Long.MAX_VALUE) return -1;
        else {
            return ans * G;
        }
    }
}
