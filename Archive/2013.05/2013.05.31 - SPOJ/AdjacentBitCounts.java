package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

public class AdjacentBitCounts {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        long ans[][][] = new long[102][102][2];
        ans[1][0][0] = 1;       //0
        ans[1][0][1] = 1;       //1
        long LIM = 10000000000l;
        for (int n = 2; n < 101; n++) {
            //add 0
            for (int k = 0; k < n; k++) {
                if (ans[n][k][0] < LIM) ans[n][k][0] += ans[n - 1][k][0];
                if (ans[n][k][0] < LIM) ans[n][k][0] += ans[n - 1][k][1];
            }
            //add 1
            for (int k = 0; k < n; k++) {
                if (ans[n][k][1] < LIM) ans[n][k][1] += ans[n - 1][k][0];
                if (ans[n][k + 1][1] < LIM) ans[n][k + 1][1] += ans[n - 1][k][1];
            }
            for (int k = 0; k < n; k++) {
                //System.out.printf("%d %d  ",ans[n][k][0], ans[n][k][1]);
            }
            //System.out.println("");
        }

        for (int i = 0; i < 10; i++) {

            //System.out.println("");
        }

        testNumber = in.readInt();
        while (testNumber-- > 0) {
            int tn = in.readInt();
            int n = in.readInt();
            int k = in.readInt();
            out.printLine(tn, ans[n][k][0] + ans[n][k][1]);
        }
    }
}
