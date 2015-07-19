package Tasks;

import javaUtils.ArrayUtils;

public class NoRepeatPlaylist {
    long[][][] dp;
    long MOD = 1000000007;
    int[] pre;
    int n, m, p;

    long go(int i, int played, int prePlayed) {
        if (i == p) {
            if (played == n) return 1;
            else return 0;
        }
        long ret = 0;
        assert (played <= n && prePlayed <= n);
        if (dp[i][played][prePlayed] == -1) {

            if (i > m) ret += (prePlayed * go(i + 1, played, prePlayed)) % MOD;
            if (ret >= MOD) ret %= MOD;
            if (played < n) ret += ((n - played) * go(i + 1, played + 1, prePlayed + (i < m ? 0 : 1))) % MOD;
            if (ret >= MOD) ret -= MOD;
            dp[i][played][prePlayed] = ret;
        }
        return dp[i][played][prePlayed];
    }

    public int numPlaylists(int N, int M, int P) {
        n = N;
        m = M;
        p = P;
        dp = new long[P][N + 1][N + 1];
        ArrayUtils.fill3d(dp, -1);
        return (int) go(0, 0, 0);
    }
}
