package Tasks;

import javaUtils.InReader;
import javaUtils.IntegerUtils;
import javaUtils.OutputWriter;

public class q3 {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        boolean table[] = IntegerUtils.generatePrimalityTable(1000006);
        int ch = in.readInt();
        int n = 0;
        for (int i = 0; i < table.length; i++) {
            if (table[i]) n++;
            if (i == ch) break;
        }
        System.out.println(n);
        int run = 1;
        int row = 1;
        int col = 1;
        while (true) {
            if (n > run) {
                n -= run;
                run++;
                row++;
                continue;
            } else {
                col = n;
                break;
            }
        }
        out.printLine(row, col);
    }

}
