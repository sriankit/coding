package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

public class RectangularQueries {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();

        int[][] grid = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = in.readInt() - 1;
            }
        }

        int dp[][][] = new int[10][n + 1][n + 1];

        for (int c = 0; c < 10; c++) {
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    dp[c][i][j] = dp[c][i - 1][j] + dp[c][i][j - 1] - dp[c][i - 1][j - 1];
                    dp[c][i][j] += grid[i - 1][j - 1] == c ? 1 : 0;
                }
            }
        }

        int q = in.readInt();
        while (q-- > 0) {
            int x1 = in.readInt();
            int y1 = in.readInt();
            int x2 = in.readInt();
            int y2 = in.readInt();
            int ans = 0;
            for (int c = 0; c < 10; c++) {
                int cnt = dp[c][x2][y2] - dp[c][x1 - 1][y2] - dp[c][x2][y1 - 1] + dp[c][x1 - 1][y1 - 1];
                if (cnt > 0) ans++;
            }
            out.printLine(ans);

        }


    }
}
