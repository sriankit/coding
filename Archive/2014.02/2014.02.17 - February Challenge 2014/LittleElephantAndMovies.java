package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.IntegerUtils;
import javaUtils.OutputWriter;

import java.util.Arrays;

public class LittleElephantAndMovies {
    long dp[][];
    int a[];
    long MOD = (long) (1e9 + 7);

    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n, k;
        n = in.readInt();
        k = in.readInt();
        a = IOUtils.readIntArray(in, n);
        Arrays.sort(a);
        dp = new long[n][k + 1];
        long C[][] = IntegerUtils.generateBinomialCoefficients(250, MOD);
        long F[] = IntegerUtils.generateFactorial(250, MOD);

        dp[n - 1][1] = 1;

        for (int i = 1; i <= k; i++) {
            for (int j = n - 2; j >= 0; j--) {
                for (int q = j + 1; q < n; q++) {
                    long P = (C[n - j - 1 - 1][n - q - 1] * F[q - j - 1]) % MOD;
                    if (a[j] == a[q]) {
                        P = (dp[q][i] * P) % MOD;
                    } else {
                        P = (dp[q][i - 1] * P) % MOD;
                    }
                    dp[j][i] += P;
                    if (dp[j][i] >= MOD) dp[j][i] -= MOD;
                }
            }
        }

        for (int j = 1; j <= k; j++) {
            for (int i = n - 1; i >= 0; i--) {
                dp[i][j] = (dp[i][j] * C[n - 1][n - i - 1]) % MOD;
                dp[i][j] = (dp[i][j] * F[i]) % MOD;
            }
        }

        long ans = 0;

        for (int i = 0; i < n; i++)
            for (int j = 0; j <= k; j++)
                ans = (ans + dp[i][j]) % MOD;

        out.printLine(ans);
    }
}