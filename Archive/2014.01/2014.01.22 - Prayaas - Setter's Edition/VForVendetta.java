package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

public class VForVendetta {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int t = in.readInt();
        while (t-- > 0) {
            long n = in.readInt();
            out.printLine(2 * n * n - n + 1);
        }
    }
}
