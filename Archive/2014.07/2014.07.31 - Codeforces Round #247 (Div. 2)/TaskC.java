package Tasks;

import javaUtils.InReader;
import javaUtils.Manacher;
import javaUtils.OutputWriter;
import org.omg.CORBA.MARSHAL;

public class TaskC {
    public static final long MOD = (long) (1e9 + 7);

    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int k = in.readInt();
        int d = in.readInt();
        long[][] dp = new long[n + 1][2];
        dp[0][0] = 1;
        dp[0][1] = 0;
        for (int i = 1; i <= n; i++) {
            for (int l = 1; l <= Math.min(k, d - 1); l++) {
                if(i - l < 0) break;
                dp[i][0] += dp[i - l][0];
                if(dp[i][0] >= MOD) dp[i][0] -= MOD;
                dp[i][1] += dp[i - l][1];
                if(dp[i][1] >= MOD) dp[i][1] -= MOD;
            }
            for (int j = d; j <= Math.min(i, k); j++) {
                dp[i][1] += dp[i - j][0] + dp[i - j][1];
                dp[i][1] %= MOD;
            }
        }
        out.printLine(dp[n][1]);
    }
}
