package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

public class CubeCakes {
    int n;
    int match[][][];

    int go(int d, int pass) {
        int cnt = 0;
        int product = (d + 1) * (d + 1) * (d + 1);
        for (int i = 1; i + d <= n; i++) {
            for (int j = 1; j + d <= n; j++) {
                for (int k = 1; k + d <= n; k++) {
                    int ii = i + d;
                    int jj = j + d;
                    int kk = k + d;
                    int sum = match[ii][jj][kk] - match[i - 1][jj][kk] - match[ii][j - 1][kk] - match[ii][jj][k - 1] + match[i - 1][j - 1][kk] + match[i - 1][jj][k - 1] + match[ii][j - 1][k - 1] - match[i - 1][j - 1][k - 1];
                    if (sum * 100 >= (pass * product)) {
                        cnt++;
                    }
                }
            }
        }
        return cnt;
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        n = in.readInt();
        int p = in.readInt();
        String s1 = in.readString();
        String s2 = in.readString();
        match = new int[n + 1][n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                for (int k = 1; k <= n; k++) {
                    int ind = (i - 1) * n * n + (j - 1) * n + k - 1;
                    match[i][j][k] = s1.charAt(ind) == s2.charAt(ind) ? 1 : 0;
                    //System.out.println(s1.charAt(ind) + " " + s2.charAt(ind) + " " + k + " " + i + " " + j);
                }
            }
            //System.out.println(Arrays.deepToString(match[k]));

        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                for (int k = 1; k <= n; k++) {
                    match[i][j][k] += (match[i - 1][j][k] + match[i][j - 1][k] + match[i][j][k - 1] - match[i - 1][j - 1][k] - match[i - 1][j][k - 1] - match[i][j - 1][k - 1] + match[i - 1][j - 1][k - 1]);
                }
            }
        }

        for (int sz = n; sz >= 1; sz--) {
            int count = go(sz - 1, p);
            if (count > 0) {
                out.printLine(sz, count);
                return;
            }
        }
        out.printLine(-1);
    }
}
