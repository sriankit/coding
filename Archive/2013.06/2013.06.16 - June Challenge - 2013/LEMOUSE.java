package Tasks;

import javaUtils.ArrayUtils;
import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

public class LEMOUSE {
    char[][] table;
    int n, m;
    int dp[][][][];
    boolean flag[][];

    int go(int curi, int curj, int m1, int m2) {
        //int mode = (curj == prej) ? 0 : 1;
        if (dp[curi][curj][m1][m2] != -1) return dp[curi][curj][m1][m2];
        int ret = 0;

        boolean[] chosen = new boolean[5];
        int[][] dx = new int[][]{{0, 0}, {-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int i = 0; i < chosen.length; i++) {
            if (check(curi + dx[i][0], curj + dx[i][1], curi, curj) && !flag[curi + dx[i][0]][curj + dx[i][1]]) {
                chosen[i] = flag[curi + dx[i][0]][curj + dx[i][1]] = true;
                ret++;
            }
        }
        int cnt1 = Integer.MAX_VALUE, cnt2 = Integer.MAX_VALUE;
        if (curi + 1 < n) cnt1 = go(curi + 1, curj, m2, 1);
        if (curj + 1 < m) cnt2 = go(curi, curj + 1, m2, 2);
        cnt1 = Math.min(cnt1, cnt2);
        if (cnt1 < Integer.MAX_VALUE) ret += cnt1;

        for (int i = 0; i < chosen.length; i++) {
            if (chosen[i]) flag[curi + dx[i][0]][curj + dx[i][1]] = false;
        }
        return dp[curi][curj][m1][m2] = ret;
    }

    boolean check(int i, int j, int x, int y) {
        return i >= 0 && j >= 0 && i < n && j < m && table[i][j] == '1' && x >= 0 && y >= 0 && x < n && x < m && (Math.abs(i - x) + Math.abs(j - y) <= 1);
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        testNumber = in.readInt();
        while (testNumber-- > 0) {
            n = in.readInt();
            m = in.readInt();
            dp = new int[n][m][3][3];
            flag = new boolean[n][m];
            for (int[][][] adp : dp) ArrayUtils.fill3d(adp, -1);
            table = IOUtils.readTable(in, n, m);
            out.printLine(go(0, 0, 0, 0));
        }
    }
}
