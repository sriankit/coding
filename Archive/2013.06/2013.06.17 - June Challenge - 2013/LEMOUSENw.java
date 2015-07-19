package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

public class LEMOUSENw {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        testNumber = in.readInt();
        while (testNumber-- > 0) {
            int n = in.readInt();
            int m = in.readInt();
            char[][] arr = IOUtils.readTable(in, n, m);
            int[][] maze = new int[n + 1][m + 1];
            int[][] mem = new int[n + 1][m + 1];

            int[][] ar = new int[n + 1][m + 1];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    ar[i][j] = (arr[i][j] == '1') ? 1 : 0;
                }
            }

            maze[0][0] = ar[0][0] + ar[0][1] + ar[1][0];
            for (int i = 1; i < m; i++) {
                maze[0][i] = maze[0][i - 1] + ar[1][i] + ar[0][i + 1];
            }
            for (int i = 1; i < n; i++) {
                maze[i][0] = maze[i - 1][0] + ar[i][1] + ar[i + 1][0];
                mem[i][0] = 1;
            }
            for (int i = 1; i < n; i++) {
                for (int j = 1; j < m; j++) {
                    int b = maze[i][j - 1];
                    if (mem[i][j - 1] <= 0) {
                        b += ar[i - 1][j];
                    }
                    int up = maze[i - 1][j];
                    if (mem[i - 1][j] == 1) {
                        up += ar[i][j - 1];
                    }
                    int ans = b;
                    if (b > up) {
                        mem[i][j] = 1;
                        ans = up;
                    } else if (b == up) {
                        mem[i][j] = 2;
                    }
                    maze[i][j] = ans + ar[i + 1][j] + ar[i][j + 1];
                }
            }
            out.printLine(maze[n - 1][m - 1]);
        }
    }
}
