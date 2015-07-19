package Tasks;

import javaUtils.InReader;
import javaUtils.IntegerUtils;
import javaUtils.OutputWriter;

public class GridLines {
    long MOD = 1000000007;
    long[][] C;
    int k;

    int numOfPoints(int x1, int y1, int x2, int y2) {
        int a = Math.abs(x1 - x2);
        int b = Math.abs(y1 - y2);
        return (int) (IntegerUtils.gcd(a, b)) + 1;
    }

    long getWays(int x1, int y1, int x2, int y2) {
        int cnt = numOfPoints(x1, y1, x2, y2);
        long choose = C[cnt - 2][k - 1];
        return (choose * 2 + C[cnt - 2][k - 2]) % MOD;
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int m = in.readInt();
        k = in.readInt();
        long ans = 0;
        C = IntegerUtils.generateBinomialCoefficients(3005, MOD);

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= m; j++) {
                long add = getWays(i, 0, j, n);
                ans += add;
                if (ans >= MOD) ans %= MOD;
            }
        }

        //System.out.println("ans = " + ans);

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                long add = getWays(0, i, m, j);
                ans += add;
                if (ans >= MOD) ans %= MOD;
            }
        }

        //System.out.println("ans = " + ans);

        long sub = getWays(0, 0, m, n);
        ans -= sub;
        if (ans < 0) ans += MOD;

        sub = getWays(0, n, m, 0);
        ans -= sub;
        if (ans < 0) ans += MOD;

        ans %= MOD;
        if (ans < 0) ans += MOD;
        out.printLine(ans);

    }
}
