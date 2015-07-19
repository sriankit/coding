package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

public class AAAAA {
    boolean ok[][];
    int r, c;
    int dp[][][][];

    int go(int i, int j, int dir, int streak) {
        if (i < 0 || i >= r || j >= c || j < 0) return 0;
        if (!ok[i][j]) return 0;
        ok[i][j] = false;
        int m = 0;
        if (dir != 0) {          //see for previous streak
            if (streak != 0) {   //extend if it may be extended
                if (dir == 1) m = Math.max(m, go(i, j - 1, dir, 1));
                if (dir == 2) m = Math.max(m, go(i - 1, j, dir, 1));
            }
        } else {                //no streak yet, begin new
            m = Math.max(m, go(i - 1, j, 2, 1));    //begin up
            m = Math.max(m, go(i, j - 1, 1, 1));    //begin left
        }
        m = Math.max(m, go(i, j + 1, dir, 0));      // routine movement right
        m = Math.max(m, go(i + 1, j, dir, 0));      // routine movement down
        ok[i][j] = true;
        return m + 1;
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        out.print("Case #" + testNumber + ": ");
        r = in.readInt();
        c = in.readInt();
        dp = new int[r][c][3][2];
        char[][] table = IOUtils.readTable(in, r, c);
        ok = new boolean[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                ok[i][j] = (table[i][j] == '.');
            }
        }
        int ans = go(0, 0, 0, 0);
        out.printLine(ans);
        System.out.println(ans);
    }
}
