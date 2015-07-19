package Tasks;

import javaUtils.ArrayUtils;

public class P8XGraphBuilder {
    int[] scores;
    int n;
    int table[][];

    int cnt(int sz, int notRoot) {
        if (sz == 1) return scores[-1 + notRoot];
        if (table[sz][notRoot] != -1) return table[sz][notRoot];
        int dp[][] = new int[sz][sz];
        //dp[0][1] = scores[0];
        --sz;
        int ret = cnt(sz, 1) + scores[0 + notRoot];
        //System.out.println("ret = " + ret);
        for (int i = 1; i <= sz; i++) {
            dp[1][i] = cnt(i, 1) + scores[0 + notRoot];
        }
        for (int degree = 2; degree <= sz; degree++) {
            for (int k = 1; k <= sz; k++) {
                for (int j = 1; j <= k - degree + 1; j++) {
                    dp[degree][k] = cnt(j, 1) + dp[degree - 1][k - j] - scores[degree - 1 - 1 + notRoot] + scores[degree - 1 + notRoot];
                }
                ret = Math.max(dp[degree][sz], ret);
            }
        }
        return table[sz + 1][notRoot] = ret;
    }

    public int solve(int[] scores) {
        this.scores = scores;
        n = scores.length + 1;
        table = new int[n + 1][2];
        ArrayUtils.fill2d(table, -1);
        return cnt(n, 0);
    }
}
