package Tasks;

import javaUtils.InReader;
import javaUtils.IntegerUtils;
import javaUtils.OutputWriter;

public class GameOfThronesII {
    long MOD = 1000000007;

    public void solve(int testNumber, InReader in, OutputWriter out) {
        String s = in.readString();
        int n = s.length();
        long[] facs = IntegerUtils.generateFactorial(100005, MOD);
        long[] inv = IntegerUtils.generateReverseFactorials(100005, MOD);
        long ans = facs[n / 2];
        int[] cnt = new int[26];
        for (int i = 0; i < n; i++) {
            cnt[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < 26; i++) {
            ans = (ans * inv[cnt[i] / 2]);
            if (ans >= MOD) ans %= MOD;
        }
        out.printLine(ans);
    }
}
