package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

public class Walk {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int[] a = IOUtils.readIntArray(in, n);
        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, a[i] + i);
        }
        out.printLine(ans);
    }
}
