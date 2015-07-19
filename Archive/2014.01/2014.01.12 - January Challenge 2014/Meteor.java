package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;
import javaUtils.Pair;

import java.util.Stack;

public class Meteor {
    int[][] h, dp;

    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int m = in.readInt();
        int k = in.readInt();
        h = new int[n + 1][m + 1];
        for (int i = 0; i < k; i++) {
            int x = in.readInt();
            int y = in.readInt();
            h[x][y] = 1;
        }
        /*for (int i = 1; i <= n; i++) {
            System.out.println(Arrays.toString(h[i]));
        }
        System.out.println("\n");*/
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (h[i][j] == 0) h[i][j] = h[i - 1][j] + 1;
                else h[i][j] = 0;
            }
        } /*
        for (int i = 1; i <= n; i++) {
            System.out.println(Arrays.toString(h[i]));
        }
        System.out.println("\n");
            */
        dp = new int[n + 2][n + 2];
        for (int i = 1; i <= n; i++) {
            int[] dpLeft = new int[m + 1];
            int[] dpRyt = new int[m + 1];

            Stack<Pair<Integer, Integer>> st = new Stack<Pair<Integer, Integer>>();

            for (int j = 1; j <= m; j++) {
                int sum = 0;
                while (!st.isEmpty()) {
                    Pair<Integer, Integer> p = st.peek();
                    if (p.first >= h[i][j]) {
                        sum += p.second;
                        st.pop();
                    } else break;
                }

                st.push(Pair.makePair(h[i][j], sum + 1));
                dpLeft[j] = sum + 1;
            }

            st.clear();
            //do the same thing in reverse
            for (int j = m; j > 0; j--) {
                int sum = 0;
                while (!st.isEmpty()) {
                    Pair<Integer, Integer> p = st.peek();
                    if (p.first >= h[i][j]) {
                        sum += p.second;
                        st.pop();
                    } else break;
                }

                st.push(Pair.makePair(h[i][j], sum + 1));
                dpRyt[j] = sum + 1;
            }

            int[] dpLocal = new int[1111];
            for (int j = 1; j <= m; j++) {
                dpLocal[h[i][j]] = Math.max(dpLocal[h[i][j]], dpLeft[j] + dpRyt[j] - 1);
            }

            int len = 0;

            for (int j = 1; j <= i; ++j) {
                len = Math.max(len, dpLocal[i - j + 1]);
                dp[i][j] = (i - j + 1) * len;
            }

            for (int j = i; j >= 1; --j) {
                dp[i][j] = Math.max(dp[i][j], Math.max(dp[i][j + 1], dp[i - 1][j]));
            }
            /*if(i == n - 1) {
                /*System.out.println(Arrays.toString(dpLeft));
                System.out.println(Arrays.toString(dpRyt));
            } */
        }

        int queryCount = in.readInt();
        while (queryCount-- > 0) {
            int lo = in.readInt();
            int hi = in.readInt();
            out.printLine(dp[hi][lo]);
        }

    }
}
