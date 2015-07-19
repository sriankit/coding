package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

public class MindYourLanguage {
    boolean[][] block;
    int n;
    long table[][];
    long MOD = 1000000000 + 7;

    public void solve(int testNumber, InReader in, OutputWriter out) {
        testNumber = in.readInt();
        while (testNumber-- > 0) {
            block = new boolean[26][26];
            for (int i = 0; i < 26; i++) {
                int bad = in.readInt();
                for (int j = 0; j < bad; j++) {
                    char c = in.readCharacter();
                    //System.out.println("read" + c);
                    int ci = (int) (c - 'a');
                    block[i][ci] = true;
                    block[ci][i] = true;
                }
            }
            n = in.readInt();
            table = new long[n][26];
            for (int i = 0; i < 26; i++) {
                table[0][i] = 1;
            }
            for (int i = 1; i < n; i++) {
                for (int j = 0; j < 26; j++) {
                    for (int k = 0; k < 26; k++) {
                        if (!block[k][j] && !block[j][k]) {
                            table[i][j] += table[i - 1][k];
                            //if(table[i][j] >= MOD) table[i][j] -= MOD;
                            table[i][j] %= MOD;
                        }
                    }
                }
            }

            long ans = 0;
            if (n == 0) {
                out.printLine(0);
                continue;
            }
            for (int i = 0; i < 26; i++) {
                ans += table[n - 1][i];
                if (ans >= MOD) ans %= MOD;
            }
            out.printLine(ans % MOD);
        }
    }

    private long go(int i, int last) {
        if (i == n) return 1;
        if (table[i][last] != -1) return table[i][last];
        long ret = 0;
        for (int j = 0; j < 26; j++) {
            if (!block[last][j]) ret = (ret + go(i + 1, j)) % MOD;
        }
        return table[i][last] = ret;
    }
}
