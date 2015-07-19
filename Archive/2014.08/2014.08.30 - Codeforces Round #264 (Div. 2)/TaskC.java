package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;
import javaUtils.Pair;

import java.util.Arrays;

public class TaskC {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();        
        int a[][] = new int[n][n];
        for (int i = 0; i < n; i++) {
            a[i] = IOUtils.readIntArray(in, n);
        }
        long d1[] = new long[2 * n - 1];
        long d2[] = new long[2 * n - 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int ind = i + j;
                d1[ind] += a[i][j];
                ind = i - j + n - 1;
                d2[ind] += a[i][j];
            }
        }
        long[][] up = new long[n][n];
        long[][] lft = new long[n][n];
        long[][] dwn = new long[n][n];
        long[][] ryt = new long[n][n];
        Pair<Integer, Integer> upi[][] = new Pair[n][n];
        Pair<Integer, Integer> lfti[][] = new Pair[n][n];
        Pair<Integer, Integer> dwni[][] = new Pair[n][n];
        Pair<Integer, Integer> ryti[][] = new Pair[n][n];
        for (int j = 0; j < n; j++) {
            int i1 = 0 + j;
            int i2 = 0 - j + n - 1;
            up[0][j] = d1[i1] + d2[i2] - a[0][j];
            upi[0][j] = Pair.makePair(0, j);
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                up[i][j] = d1[i + j] + d2[i - j + n - 1] - a[i][j];
                upi[i][j] = Pair.makePair(i, j);
                if(j - 1 >= 0) up[i][j] = Math.max(up[i][j], up[i - 1][j - 1]);
                if(j + 1 < n) up[i][j] = Math.max(up[i][j], up[i - 1][j + 1]);
                if(j - 1 >= 0) upi[i][j] = (up[i][j] < up[i - 1][j - 1]) ? upi[i - 1][j - 1] : upi[i][j];
                if(j + 1 < n) upi[i][j] = (up[i][j] < up[i - 1][j + 1]) ? upi[i - 1][j + 1] : upi[i][j];
            }    
        }

        for (int j = 0; j < n; j++) {
            int i1 = n - 1 + j;
            int i2 = n - 1 - j + n - 1;
            dwn[n - 1][j] = d1[i1] + d2[i2] - a[n - 1][j];
            dwni[n - 1][j] = Pair.makePair(n - 1, j);
        }
        for (int i = n - 2; i >= 0; i--) {
            for (int j = 0; j < n; j++) {
                dwn[i][j] = d1[i + j] + d2[i - j + n - 1] - a[i][j];
                if(j - 1 >= 0) dwn[i][j] = Math.max(dwn[i][j], dwn[i + 1][j - 1]);
                if(j + 1 < n) dwn[i][j] = Math.max(dwn[i][j], dwn[i + 1][j + 1]);
                dwni[i][j] = Pair.makePair(i, j);
                if(j - 1 >= 0) dwni[i][j] = (dwn[i][j] < dwn[i + 1][j - 1]) ? dwni[i + 1][j - 1] : dwni[i][j];
                if(j + 1 < n) dwni[i][j] = (dwn[i][j] < dwn[i + 1][j + 1]) ? dwni[i + 1][j + 1] : dwni[i][j];
            }
        }

        for (int i = 0; i < n; i++) {
            lft[i][0] = d1[i] + d2[i + n - 1] - a[i][0];
            lfti[i][0] = Pair.makePair(i, 0);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < n; j++) {
                lft[i][j] = d1[i + j] + d2[i - j + n - 1] - a[i][j];
                if(i - 1 >= 0) lft[i][j] = Math.max(lft[i][j], lft[i - 1][j - 1]);
                if(i + 1 < n) lft[i][j] = Math.max(lft[i][j], lft[i + 1][j - 1]);
                lfti[i][j] = Pair.makePair(i, j);
                if(i - 1 >= 0) lfti[i][j] = (lft[i][j] < lft[i - 1][j - 1]) ? lfti[i - 1][j - 1] : lfti[i][j];
                if(i + 1 < n) lfti[i][j] = (lft[i][j] < lft[i + 1][j - 1]) ? lfti[i + 1][j - 1] : lfti[i][j];
            }
        }

        for (int i = 0; i < n; i++) {
            ryt[i][n - 1] = d1[i + n - 1] + d2[i] - a[i][n - 1];
            ryti[i][n - 1] = Pair.makePair(i, n - 1);
        }
        for (int i = 0; i < n; i++) {
            for (int j = n - 2; j >= 0; j--) {
                ryt[i][j] = d1[i + j] + d2[i - j + n - 1] - a[i][j];
                if(i - 1 >= 0) ryt[i][j] = Math.max(ryt[i][j], ryt[i - 1][j + 1]);
                if(i + 1 < n) ryt[i][j] = Math.max(ryt[i][j], ryt[i + 1][j + 1]);
                ryti[i][j] = Pair.makePair(i, j);
                if(i - 1 >= 0) ryti[i][j] = (ryt[i][j] < ryt[i - 1][j + 1]) ? ryti[i - 1][j + 1] : ryti[i][j];
                if(i + 1 < n) ryti[i][j] = (ryt[i][j] < ryt[i + 1][j + 1]) ? ryti[i + 1][j + 1] : ryti[i][j];
            }
        }

        long mx = 0, i1 = 0, j1 = 0, i2 = 0, j2 = 0;

        //System.out.println(Arrays.deepToString(lft));
        //System.out.println(Arrays.toString(d1));
        //System.out.println(Arrays.toString(d2));

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                long x = d1[i + j] + d2[i - j + n - 1] - a[i][j];
                long y = 0;
                Pair yi = null;
                if(j - 1 >= 0) y = Math.max(y, lft[i][j - 1]);
                if(j + 1 < n) y = Math.max(y, ryt[i][j + 1]);
                if(i - 1 >= 0) y = Math.max(y, up[i - 1][j]);
                if(i + 1 < n) y = Math.max(y, dwn[i + 1][j]);
                if(j - 1 >= 0) if (y == lft[i][j - 1]) yi = lfti[i][j - 1];
                if(j + 1 < n) if (y == ryt[i][j + 1]) yi = ryti[i][j + 1];
                if(i - 1 >= 0) if(y == up[i - 1][j]) yi = upi[i - 1][j];
                if(i + 1 < n) if(y == dwn[i + 1][j]) yi = dwni[i + 1][j];

                if(x + y > mx) {
                    i1 = i + 1;
                    j1 = j + 1;
                    i2 = (int)yi.first + 1;
                    j2 = (int)yi.second + 1;
                    mx = x + y;
                }
            }
        }
        out.printLine(mx);
        out.printLine(i1 + " " + j1 + " " + i2 + " " + j2);
    }
}
