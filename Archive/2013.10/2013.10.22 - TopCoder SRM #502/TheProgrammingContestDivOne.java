package Tasks;

public class TheProgrammingContestDivOne {
    public int find(int T, int[] M, int[] P, int[] R) {
        //We use 64 bit integers because high values of
        // P and R may overflow when multiplied.


        int n = M.length;
        //sort by R[i]/P[i] (using bubble sort for the sake of simplicity)
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                // We want to sort by R/P which translates to:
                // R[i]/P[i] < R[j]/P[j]
                // doubles can be problematic to use in comparison,
                // we can get rid of the requirement for doubles if
                // we use:

                if (!((long) R[i] * P[j] < (long) R[j] * P[i])) {
                    // R[i]/P[i] is not < R[j]/P[j] swap them:
                    int tm;
                    tm = R[i];
                    R[i] = R[j];
                    R[j] = tm;
                    tm = M[i];
                    M[i] = M[j];
                    M[j] = tm;
                    tm = P[i];
                    P[i] = P[j];
                    P[j] = tm;
                }
            }
        }

        long[][] dp = new long[n + 1][T + 1];
        // the i==n case will be set to 0 by default.

        for (int i = n - 1; i >= 0; i--) {
            for (int t = 0; t <= T; t++) {
                long res = 0;
                if (t + R[i] <= T) {
                    // use it:
                    res = M[i] - (long) P[i] * (t + R[i]);
                    res += dp[i + 1][t + R[i]];
                }
                // do not use it:
                res = Math.max(res, dp[i + 1][t]);

                dp[i][t] = res;
            }
        }
        // The result is when the recurrence starts with t=0, and i=0
        return (int) dp[0][0];

    }
}
