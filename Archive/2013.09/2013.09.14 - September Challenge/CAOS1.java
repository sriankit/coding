package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

public class CAOS1 {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int r = in.readInt();
        int c = in.readInt();
        char[][] arr = IOUtils.readTable(in, r, c);
        int cnt = 0;
        for (int i = 2; i < r - 2; i++) {
            for (int j = 2; j < c - 2; j++) {
                int[] instant = {-2, -1, 0, 1, 2};
                boolean res = true;
                for (int k = 0; k < 5 && res; k++) {
                    res = arr[i + instant[k]][j] == '^' && arr[i][j + instant[k]] == '^';
                }
                if (res) cnt++;
            }
        }
        out.printLine(cnt);
    }
}
