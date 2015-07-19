package Tasks;

import java.util.Arrays;

public class PerfectMemory {
    int total;
    double dp[][];

    double go(int unknownPairs, int know1) {
        double t = 2 * unknownPairs + know1;
        if ((int) t == 0) return 0;
        if (dp[unknownPairs][know1] != -1) return dp[unknownPairs][know1];
        //pick an unknown card
        //know1 card picked
        double p = (know1 / t);
        double exp = 0;
        if (know1 > 0) exp = p * (1 + go(unknownPairs, know1 - 1));
        //unknown pair card picked
        //1.got pair
        if (unknownPairs > 0) {
            exp += (1 - p) * (1 / (t - 1)) * (1 + go(unknownPairs - 1, know1));
            //2.got know1
            exp += (1 - p) * (know1 / (t - 1)) * (2 + go(unknownPairs - 1, know1 - 1 + 1));
            //3.got another unknown
            if (unknownPairs > 1)
                exp += (1 - p) * ((unknownPairs * 2 - 2) / (t - 1)) * (1 + go(unknownPairs - 2, know1 + 2));
        }
        return dp[unknownPairs][know1] = exp;
    }

    public double getExpectation(int N, int M) {
        int tot = N * M / 2;
        dp = new double[tot + 1][tot + 1];
        for (double[] ar : dp) Arrays.fill(ar, -1);
        return go(N * M / 2, 0);
    }
}
