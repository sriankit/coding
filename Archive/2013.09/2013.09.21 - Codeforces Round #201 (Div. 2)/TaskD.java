package Tasks;

import javaUtils.AhoCorasickDataStructure;
import javaUtils.ArrayUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.ArrayList;

public class TaskD {
    AhoCorasickDataStructure ds;
    int[][][] dp;
    String s1, s2;

    int go(int i, int j, int state) {
        if (dp[i][j][state] == -1) {
            dp[i][j][state] = -100000;
            if (!ds.found[state]) {
                if (i >= s1.length() || j >= s2.length()) dp[i][j][state] = 0;
                else {
                    dp[i][j][state] = Math.max(go(i + 1, j, state), go(i, j + 1, state));
                    if (s1.charAt(i) == s2.charAt(j))
                        dp[i][j][state] = Math.max(dp[i][j][state], go(i + 1, j + 1, ds.trans[state][s1.charAt(i) - 'A']) + 1);
                }
            }
        }
        return dp[i][j][state];
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        s1 = in.readString();
        s2 = in.readString();
        String virus = in.readString();
        ds = new AhoCorasickDataStructure(26, 'A');
        ds.add(virus, 0);
        ds.bfs();
        ds.makeTrans();
        dp = new int[s1.length() + 1][s2.length() + 1][virus.length() + 1];
        ArrayUtils.fill3d(dp, -1);

        /*for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                for (int state = 0; state < virus.length(); state++) {
                    if(i == 0 || j == 0 || ds.found[state]) {
                        dp[i][j][state] = 0;
                        continue;
                    }
                    dp[i][j][state] = Math.max(Math.max(dp[i - 1][j][state], dp[i][j - 1][state]), dp[i][j][state]);
                    if(s1.charAt(i - 1) == s2.charAt(j - 1) && ds.trans[state][s1.charAt(i - 1) - 'A'] != virus.length()) {
                        dp[i][j][ds.trans[state][s1.charAt(i - 1) - 'A']] = Math.max(dp[i - 1][j - 1][state] + 1, dp[i][j][ds.trans[state][s1.charAt(i - 1) - 'A']]);
                        //System.out.println("updated " + i + "  "+ j + "  "+ state + " changed to " + dp[i][j][ds.trans[state][s1.charAt(i - 1) - 'A']]);
                    }
                }
            }
        }
        for (int i = 0; i < s1.length() + 1; i++) {
            System.out.println(Arrays.deepToString(dp[i]));
        } */
        int i = 0, j = 0, state = 0;
        ArrayList<Character> ans = new ArrayList<Character>();
        while (i < s1.length() && j < s2.length()) {
            if (go(i, j, state) == go(i + 1, j, state)) i++;
            else if (go(i, j, state) == go(i, j + 1, state)) j++;
            else {
                ans.add(s1.charAt(i));
                state = ds.trans[state][s1.charAt(i) - 'A'];
                i++;
                j++;
            }
        }
        if (ans.size() == 0) out.printLine(0);
        else for (char c : ans) out.print(c);

        //System.out.println(dp[s1.length()][s2.length()]);
    }
}
