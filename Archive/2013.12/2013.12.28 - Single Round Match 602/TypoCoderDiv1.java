package Tasks;

import javaUtils.ArrayUtils;

public class TypoCoderDiv1 {
    int[] d;
    int n;

    int dp[][][];

    int go(int x, int brown, int i) {
        if (i == n) return 0;
        if (dp[x][brown][i] == -1) {
            if (brown == 1) {
                long cur = x + d[i - 1];
                if (cur - d[i] < 2200) dp[x][brown][i] = 1 + go(Math.max(0, (int) (cur - d[i])), 0, i + 1);
            } else {
                long nw = x - d[i];
                dp[x][brown][i] = go(Math.max(0, (int) nw), 0, i + 1);
                nw = x + d[i];
                int s = nw >= 2200 ? 1 : 0;
                dp[x][brown][i] = Math.max(dp[x][brown][i], s + go(s == 1 ? x : (int) nw, s, i + 1));
            }
        }
        return dp[x][brown][i];
    }

    public int getmax(int[] D, int X) {
        d = D;
        n = D.length;
        dp = new int[2300][2][n];
        ArrayUtils.fill3d(dp, -1);
        return go(X, 0, 0);
    }
}
