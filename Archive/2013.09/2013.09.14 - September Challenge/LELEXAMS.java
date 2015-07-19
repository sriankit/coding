package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

public class LELEXAMS {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        testNumber = in.readInt();
        while (testNumber-- > 0) {
            int n = in.readInt();
            double[] P = new double[n];
            int[][] A = new int[n][2];
            for (int i = 0; i < n; i++) {
                P[i] = in.readInt() / 100.0;
                A[i][0] = in.readInt();
                A[i][1] = in.readInt();
            }

            if (n > 16) out.printLine(0.0);
            else {
                double ans = 0.0;
                for (int i = 0; i < (1 << n); i++) {
                    boolean[] map = new boolean[17];
                    int res = i;
                    boolean ok = true;
                    double p = 1.0;
                    for (int j = 0; j < n; j++, res >>= 1) {
                        int c = res & 1;
                        if (c == 1) p *= 1 - P[j];
                        else p *= P[j];
                        if (map[A[j][c]]) {
                            ok = false;
                            break;
                        } else map[A[j][c]] = true;
                    }
                    if (ok) ans += p;
                }
                out.printLine(ans);
            }
        }
    }
}
