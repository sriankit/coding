package Tasks;

import javaUtils.ArrayUtils;
import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

public class TheIndianJob {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n, g;
        n = in.readInt();
        g = in.readInt();
        int[] a = IOUtils.readIntArray(in, n);
        int sum = (int) ArrayUtils.sum(a);
        if (g >= sum) {
            out.printLine("YES");
            return;
        }
        int dp[][] = new int[n][g + 1];
        for (int j = 1; j <= g; j++) {
            for (int i = 0; i < n; i++) {
                if (j >= a[i]) {
                    if (i > 0) dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - a[i]] + a[i]);
                    else dp[0][j] = Math.max(dp[0][j], a[i]);
                } else if (i > 0) dp[i][j] = dp[i - 1][j];
            }
        }
        int left = g - dp[n - 1][g];
        if (left > 2 * g - sum) {
            out.printLine("NO");
        } else {
            out.printLine("YES");
        }
        //System.out.println(Arrays.deepToString(dp));
    }
}
