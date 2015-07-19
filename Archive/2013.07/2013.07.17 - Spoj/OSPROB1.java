package Tasks;

import javaUtils.*;

public class OSPROB1 {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int[] arr = IOUtils.readIntArray(in, n);
        if (n == 0) {
            out.printLine(0, 0);
            return;
        }
        PrefixSum preSum = new PrefixSum(arr);
        //precomputing
        Pair<Long, Long> single[][] = new Pair[n][n];
        Pair<Long, Long> dp[] = new Pair[n];
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (i == j) single[i][i] = Pair.makePair((long) arr[i], (long) 0);
                else {
                    int lo = i, hi = j;
                    //single[i][j] = Pair.makePair((long)-1, (long)-1);
                    long tot = preSum.getSum(lo, hi);
                    while (lo <= hi) {
                        int mid = lo + hi >> 1;
                        long s1 = preSum.getSum(i, mid);
                        long s2 = tot - s1;
                        if (s1 > s2) {
                            single[i][j] = Pair.makePair(s1, s2);
                            hi = mid - 1;
                        } else lo = mid + 1;
                    }
                }
            }
        }

        /*for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                System.out.print(single[i][j].toString() + " ");
                //else System.out.print("None ");
            }
            System.out.println("");
        } */

        dp[n - 1] = single[n - 1][n - 1];
        for (int i = n - 2; i >= 0; i--) {
            dp[i] = single[i][n - 1];
            for (int j = i; j < n - 1; j++) {
                if (single[i][j] != null && dp[j + 1] != null) {
                    if (dp[i] == null || single[i][j].second + dp[j + 1].second > dp[i].second)
                        dp[i] = Pair.makePair(single[i][j].first + dp[j + 1].first, single[i][j].second + dp[j + 1].second);
                }
            }
        }
        /*for (int i = 0; i < n; i++) {
            System.out.print(dp[i].toString());
        }
        System.out.println("\n");*/
        out.printLine(dp[0].first, dp[0].second);
    }
}
