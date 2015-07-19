package Tasks;

import javaUtils.*;

public class MOU1H {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int t = in.readInt();
        while (t-- > 0) {
            int n = in.readInt();
            StringBuilder builder = new StringBuilder();
            int h[] = IOUtils.readIntArray(in, n);
            if (n == 1) {
                out.printLine(0);
                continue;
            }
            for (int i = 1; i < n; i++) {
                builder.append((char) ('\0' + (101 + h[i] - h[i - 1])));
            }
            int[] sa = SuffixArray.suffixArray(builder);
            int[] lcp = SuffixArray.lcp(sa, builder);
            long tot = (n - 1) * (long) n / 2;
            out.printLine((tot - ArrayUtils.sum(lcp)) % 1000000009);
        }
    }
}
