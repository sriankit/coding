package Tasks;

import javaUtils.InReader;
import javaUtils.IntegerUtils;
import javaUtils.OutputWriter;

public class IITKWPCB {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int t = in.readInt();
        while (t-- > 0) {
            long n = in.readInt();
            long ans = n / 2;
            while (ans > 0 && IntegerUtils.gcd(ans, n) > 1) ans--;
            out.printLine(ans);
        }
    }
}
