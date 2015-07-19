package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

public class ChefAndDigits {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int m = in.readInt();
        String input = " " + in.readString();
        int[] a = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            a[i] = input.charAt(i) - '0';
        }
        int[][] pre = new int[n + 1][10];
        for (int i = 1; i <= n; i++) {
            System.arraycopy(pre[i - 1], 0, pre[i], 0, 10);
            pre[i][a[i]] += 1;
        }
        for (int i = 0; i < m; i++) {
            int x = in.readInt();
            int num = a[x];
            int b1 = 0;
            int b2 = 0;
            for (int d = 1; num - d >= 0; d++) {
                b1 += d * pre[x][num - d];
            }
            for (int d = 1; num + d < 10; d++) {
                b2 -= d * pre[x][num + d];
            }
            out.printLine(b1 - b2);
        }
    }
}
