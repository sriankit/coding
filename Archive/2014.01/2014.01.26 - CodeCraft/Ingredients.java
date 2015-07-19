package Tasks;

import javaUtils.ArrayUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.ArrayList;

public class Ingredients {
    long warshall[][];

    long getMin(int start, ArrayList<Integer> list) {
        if (list.size() == 0) return 0;
        long ret = Long.MAX_VALUE;
        for (int i = 0; i < list.size(); i++) {
            ArrayList<Integer> tmp = new ArrayList<Integer>();
            for (int j = 0; j < list.size(); j++) {
                if (j != i) tmp.add(list.get(j));
            }
            ret = Math.min(ret, warshall[start][list.get(i)] + getMin(list.get(i), tmp));
        }
        return ret;
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int m = in.readInt();
        warshall = new long[n][n];
        ArrayUtils.fill2d(warshall, Integer.MAX_VALUE);
        for (int i = 0; i < n; i++) {
            warshall[i][i] = 0;
        }
        for (int i = 0; i < m; i++) {
            int a = in.readInt();
            int b = in.readInt();
            int c = in.readInt();
            warshall[a][b] = c;
            warshall[b][a] = c;
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    warshall[i][j] = Math.min(warshall[i][j], warshall[i][k] + warshall[k][j]);
                }
            }
        }

        int s = in.readInt();
        int[] need = new int[s];
        for (int i = 0; i < s; i++) {
            need[i] = in.readInt();
        }
        int s1 = in.readInt();
        int s2 = in.readInt();
        long ans = Long.MAX_VALUE;
        for (int mask = 0; mask < (1 << s); mask++) {
            String res = Integer.toBinaryString(mask);
            while (res.length() < s) res = '0' + res;
            ArrayList<Integer> ind1 = new ArrayList<Integer>();
            ArrayList<Integer> ind2 = new ArrayList<Integer>();
            for (int i = 0; i < s; i++) {
                if (res.charAt(i) == '1') ind1.add(need[i]);
                else ind2.add(need[i]);
            }
            ans = Math.min(ans, Math.min(getMin(s1, ind1) + getMin(s2, ind2), getMin(s1, ind2) + getMin(s2, ind1)));
        }
        out.printLine(ans);
    }
}
