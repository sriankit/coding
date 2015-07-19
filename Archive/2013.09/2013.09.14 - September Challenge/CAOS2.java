package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.IntegerUtils;
import javaUtils.OutputWriter;

import java.util.Arrays;

public class CAOS2 {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int[] primes = IntegerUtils.generatePrimes(505);
        testNumber = in.readInt();
        while (testNumber-- > 0) {
            int r = in.readInt();
            int c = in.readInt();
            char[][] arr = IOUtils.readTable(in, r, c);
            int[][][] A = new int[4][r][c];

            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    if (arr[i][j] == '^' && i > 0 && arr[i - 1][j] == '^') A[0][i][j] = A[0][i - 1][j] + 1;
                }
            }
            for (int i = 0; i < r; i++) {
                for (int j = c - 1; j >= 0; j--) {
                    if (arr[i][j] == '^' && j + 1 < c && arr[i][j + 1] == '^') A[1][i][j] = A[1][i][j + 1] + 1;
                }
            }
            for (int i = r - 1; i >= 0; i--) {
                for (int j = 0; j < c; j++) {
                    if (arr[i][j] == '^' && j > 0 && arr[i][j - 1] == '^') A[2][i][j] = A[2][i][j - 1] + 1;
                }
            }
            for (int i = r - 1; i >= 0; i--) {
                for (int j = c - 1; j >= 0; j--) {
                    if (arr[i][j] == '^' && i + 1 < r && arr[i + 1][j] == '^') A[3][i][j] = A[3][i + 1][j] + 1;
                }
            }

            long ans = 0;
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    int m = A[0][i][j];
                    for (int k = 1; k < 4; k++) {
                        m = Math.min(m, A[k][i][j]);
                    }
                    int cnt = 0;
                    int k = Arrays.binarySearch(primes, m);
                    if (k >= 0) cnt = k + 1;
                    else cnt = -k - 1;
                    ans += cnt;
                }
            }
            out.printLine(ans);
        }
    }
}
