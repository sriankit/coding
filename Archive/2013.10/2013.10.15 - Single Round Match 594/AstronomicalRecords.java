package Tasks;

import javaUtils.ArrayUtils;
import javaUtils.IntegerUtils;
import javaUtils.Pair;

import java.util.HashMap;

public class AstronomicalRecords {
    int dp[][][];
    int x, y;
    long[] a, b;
    HashMap<Pair<Long, Long>, Integer> mapRatio;
    Pair<Integer, Integer> rehash[];


    int go(int i, int j, int ratio) {

        if (i == x) {
            return y - j;
        }
        if (j == y) {
            return x - i;
        }

        long g = IntegerUtils.gcd(a[i], b[j]);
        Pair<Long, Long> p = Pair.makePair(a[i] / g, b[j] / g);
        int thisRatio = mapRatio.get(p);


        if (dp[i][j][ratio] != -1) return dp[i][j][ratio];

        int ans = Integer.MAX_VALUE;

        if (ratio == 0) {
            ans = Math.min(ans, 1 + go(i + 1, j + 1, thisRatio));
            ans = Math.min(ans, 1 + go(i + 1, j, 0));
            ans = Math.min(ans, 1 + go(i, j + 1, 0));
            ans = Math.min(ans, 2 + go(i + 1, j + 1, 0));
            return dp[i][j][ratio] = ans;
        }

        if (ratio == thisRatio) {
            ans = Math.min(ans, 1 + go(i + 1, j + 1, ratio));
        }

        ans = Math.min(ans, 1 + go(i + 1, j, ratio));
        ans = Math.min(ans, 1 + go(i, j + 1, ratio));


        return dp[i][j][ratio] = ans;
    }

    public int minimalPlanets(int[] A, int[] B) {
        x = A.length;
        y = B.length;
        a = new long[x];
        for (int i = 0; i < x; i++) {
            a[i] = (long) A[i];
        }
        b = new long[y];

        for (int i = 0; i < y; i++) {
            b[i] = (long) B[i];
        }


        mapRatio = new HashMap<Pair<Long, Long>, Integer>();

        int hash = 1;

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                int g = (int) IntegerUtils.gcd(a[i], b[j]);
                Pair<Long, Long> p = Pair.makePair(a[i] / g, b[j] / g);
                if (!mapRatio.containsKey(p)) {
                    //rehash[hash] = p;
                    mapRatio.put(Pair.makePair(a[i] / g, b[j] / g), hash++);
                }

            }
        }

        dp = new int[x][y][hash];

        ArrayUtils.fill3d(dp, -1);

        return go(0, 0, 0);
    }
}
