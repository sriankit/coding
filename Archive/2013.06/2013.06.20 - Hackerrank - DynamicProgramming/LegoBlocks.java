package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

public class LegoBlocks {
    int n, m;
    long dp[][], ans[];
    long MOD = 1000000007;

    public void solve(int testNumber, InReader in, OutputWriter out) {
        dp = new long[1001][1001];
        dp[1][1] = 1;
        dp[2][1] = 2;
        dp[3][1] = 4;
        dp[4][1] = 8;
        for (int i = 5; i < 1001; i++) {
            dp[i][1] = (dp[i - 1][1] + dp[i - 2][1] + dp[i - 3][1] + dp[i - 4][1]) % MOD;
        }
        for (int i = 1; i < 1001; i++) {
            for (int j = 2; j < 1001; j++) {
                dp[i][j] = (dp[i][j - 1] * dp[i][1]) % MOD;
            }
        }

        testNumber = in.readInt();
        while (testNumber-- > 0) {
            m = in.readInt();
            n = in.readInt();
            ans = new long[n + 1];
            ans[1] = dp[1][m];
            for (int i = 2; i <= n; i++) {
                long s = 0;
                for (int j = 1; j < i; j++) {
                    long add = (ans[j] * dp[i - j][m]) % MOD;
                    s = (s + add) % MOD;
                }
                ans[i] = dp[i][m] - s;
                while (ans[i] < 0) ans[i] += MOD;
            }
            out.printLine(ans[n]);
        }
    }
}
