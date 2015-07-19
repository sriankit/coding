package Tasks;

import javaUtils.InReader;
import javaUtils.IntegerUtils;
import javaUtils.OutputWriter;

public class StrangeMatrix {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int t = in.readInt();
        while (t-- > 0) {
            long m, n;
            n = in.readLong();
            m = in.readLong();
            if (n == 1 || m == 1) out.printLine(n * m);
            else out.printLine(IntegerUtils.gcd(n - 1, m - 1) + 1);
        }
    }
}
