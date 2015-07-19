package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.IntegerUtils;
import javaUtils.OutputWriter;

public class ANUCBC {
    long MOD = (long) 1e9 + 9;

    long[] facs;
    long[] invFacs;

    long nCr(int n, int r) {
        return (facs[n] * ((invFacs[r] * invFacs[n - r]) % MOD)) % MOD;
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        facs = IntegerUtils.generateFactorial(100005, MOD);
        invFacs = IntegerUtils.generateReverseFactorials(100005, MOD);

        int t = in.readInt();
        while (t-- > 0) {
            int n = in.readInt();
            int q = in.readInt();
            int[] a = IOUtils.readIntArray(in, n);
            while (q-- > 0) {
                int m = in.readInt();
                int[] b = a.clone();
                int[] buck = new int[m];
                for (int i = 0; i < n; i++) {
                    b[i] %= m;
                    if (b[i] < 0) b[i] += m;
                    buck[b[i]]++;
                }
                //System.out.println(Arrays.toString(buck));
                long[][] contribution = new long[m][m];
                for (int i = 0; i < m; i++) {
                    for (int bck = 0; bck <= buck[i]; bck++) {
                        int j = (i * bck) % m;
                        contribution[i][j] += nCr(buck[i], bck);
                        //System.out.println(buck[i] + " " + bck + " " + nCr(buck[i], bck));
                        if (contribution[i][j] >= MOD) contribution[i][j] -= MOD;
                    }
                }
                //System.out.println(Arrays.deepToString(contribution));
                long dp[][] = new long[m][m];
                for (int i = 0; i < m; i++) {
                    dp[0][i] = contribution[0][i];
                }
                for (int i = 1; i < m; i++) {
                    for (int j = 0; j < m; j++) {
                        for (int k = 0; k < m; k++) {
                            dp[i][(j + k) % m] += (dp[i - 1][j] * contribution[i][k]) % MOD;
                            if (dp[i][(j + k) % m] >= MOD) dp[i][(j + k) % m] -= MOD;
                        }
                    }
                }
                out.printLine(dp[m - 1][0]);
            }
        }

    }
}
