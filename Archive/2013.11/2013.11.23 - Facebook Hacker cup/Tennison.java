package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

public class Tennison {
    double dp[][];
    int k;
    private double probWinPresentSun;
    private double probWinAbsentSun;
    private double probSunUp;
    private double incInProb;
    private double probOfInc;
    private double decInProb;
    private double probOfDec;

    double go(int w1, int w2) {
        if (w1 == k) return 1;
        if (w2 == k) return 0;
        if (dp[w1][w2] == -1) {
            double ps = incInProb * w1 * probOfInc - decInProb * w2 * probOfDec;
            ps += probSunUp;
            ps = Math.max(ps, 0);
            ps = Math.min(ps, 1);

            double probWinSet = ps * probWinPresentSun + (1 - ps) * probWinAbsentSun;
            dp[w1][w2] = probWinSet * go(w1 + 1, w2) + (1 - probWinSet) * go(w1, w2 + 1);
        }
        return dp[w1][w2];
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        k = in.readInt();
        dp = new double[k][k];
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                dp[i][j] = -1;
            }
        }
        probWinPresentSun = Double.parseDouble(in.next());
        probWinAbsentSun = Double.parseDouble(in.next());
        probSunUp = Double.parseDouble(in.next());
        incInProb = Double.parseDouble(in.next());
        probOfInc = Double.parseDouble(in.next());
        decInProb = Double.parseDouble(in.next());
        probOfDec = Double.parseDouble(in.next());
        out.printLine("Case #" + testNumber + ": " + go(0, 0));
    }
}
