package Tasks;

import javaUtils.ArrayUtils;
import javaUtils.InReader;
import javaUtils.IntegerUtils;
import javaUtils.OutputWriter;

public class TaskA {
    int n;
    int c[], pre[];
    int tot;
    long MOD = (long) 1e9 + 7;
    long C[][];
    long dp[][];

    long go(int i, int toBlock) {
        if(dp[i][toBlock] != -1) return dp[i][toBlock];
        if(i == tot - 1) {
            if(toBlock == n - 1)
                return 1;
            else return 0;
        }
        if(i >= pre[toBlock] - 1) {
            int open = i + 1;
            if(toBlock > 0) open -= pre[toBlock - 1];
            return dp[i][toBlock] = (C[open - 1][c[toBlock] - 1] * go(i + 1, toBlock + 1) % MOD + go(i + 1, toBlock)) % MOD;
        }
        else return dp[i][toBlock] = go(i + 1, toBlock);
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        C = IntegerUtils.generateBinomialCoefficients(1001, MOD);
        n = in.readInt();
        c = new int[n];
        pre = new int[n];
        for (int i = 0; i < n; i++) {
            c[i] = in.readInt();
            if(i > 0) pre[i] = pre[i - 1] + c[i];
            else pre[i] = c[i];
        }
        tot = pre[n - 1];
        dp = new long[tot][n];
        ArrayUtils.fill2d(dp, -1);
        out.printLine(go(0, 0));
    }
}
