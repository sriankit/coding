package Tasks;

import javaUtils.ArrayUtils;

public class TravelOnMars2 {
    public int minTimes(int[] range, int startCity, int endCity) {
        int n = range.length;
        int[][] dp = new int[n][n];
        ArrayUtils.fill2d(dp, 100000);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) dp[i][i] = 0;
                else {
                    int d = Math.min((i - j + n) % n, (j - i + n) % n);
                    assert (d >= 0);
                    if (d <= range[i]) dp[i][j] = 1;
                }
            }
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k][j]);
                }
            }
        }
        return dp[startCity][endCity];
    }
}
