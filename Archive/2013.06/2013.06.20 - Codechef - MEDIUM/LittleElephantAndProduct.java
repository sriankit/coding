package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

public class LittleElephantAndProduct {
    long ans = 0;
    int[] arr1;
    int[] arr2;
    boolean dp[][][][][];

    void go(int i, int pre1, int pre2, int c4, int c7) {
        if (i == -1) {
            ans = Math.max(c4 * c7, ans);
            return;
        }
        int min = 0;
        if (pre1 == 1) min = arr1[i];
        int max = 9;
        if (pre2 == 1) max = arr2[i];
        if (dp[i][pre1][pre2][c4][c7]) return;
        for (int j = min; j <= max; j++) {
            if (j == 4) go(i - 1, (pre1 == 1 && arr1[i] == j) ? 1 : 0, (pre2 == 1 && arr2[i] == j) ? 1 : 0, c4 + 1, c7);
            else if (j == 7)
                go(i - 1, (pre1 == 1 && arr1[i] == j) ? 1 : 0, (pre2 == 1 && arr2[i] == j) ? 1 : 0, c4, c7 + 1);
            else go(i - 1, (pre1 == 1 && arr1[i] == j) ? 1 : 0, (pre2 == 1 && arr2[i] == j) ? 1 : 0, c4, c7);
        }
        dp[i][pre1][pre2][c4][c7] = true;
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        testNumber = in.readInt();
        while (testNumber-- > 0) {
            dp = new boolean[19][2][2][19][19];
            ans = 0;
            long l = in.readLong();
            long r = in.readLong();
            arr1 = new int[19];
            arr2 = new int[19];
            long res = l;
            int j = 0;
            while (res > 0) {
                arr1[j++] = (int) (res % 10);
                res /= 10;
            }

            res = r;
            j = 0;
            while (res > 0) {
                arr2[j++] = (int) (res % 10);
                res /= 10;
            }
            int first = j - 1;
            go(first, 1, 1, 0, 0);
            out.printLine(ans);
        }
    }
}
