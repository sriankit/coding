package Tasks;

import java.util.Arrays;

public class TableSeating {
    double p[];
    double dp[];
    int n;

    double go(int mask) {
        System.out.println("mask = " + mask);
        if (dp[mask] < 0) {
            int used = Integer.bitCount(mask);
            double res = 0.0;
            for (int i = 0; i < p.length; i++) {
                int want = i + 1;
                double prob = p[i];
                boolean allOk = false;
                for (int j = 0; j <= n - want; j++) {
                    boolean ok = true;
                    for (int k = 0; k < want; k++) {
                        if ((mask & (1 << (j + k))) == 1)
                            ok = false;
                    }
                    if (ok) {
                        allOk = true;
                        int pass = mask;
                        for (int k = 0; k < want; k++) {
                            pass |= (1 << (j + k));
                        }
                        res += p[i] * go(pass);
                    }
                }
                if (!allOk) res += p[i] * used;
            }
            dp[mask] = res;
        }
        return dp[mask];
    }

    public double getExpected(int numTables, int[] probs) {
        n = numTables;
        p = new double[probs.length];
        dp = new double[1 << 13];
        Arrays.fill(dp, -1);
        for (int i = 0; i < probs.length; i++) {
            p[i] = probs[i] / 100.0;
        }
        return go(0);
    }
}
