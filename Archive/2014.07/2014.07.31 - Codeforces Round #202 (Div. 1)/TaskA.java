package Tasks;

import javaUtils.ArrayUtils;
import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

public class TaskA {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int[] a = IOUtils.readIntArray(in, n);
        long lo = 1, hi = ArrayUtils.sum(a);
        long ans = hi;
        while(lo <= hi) {
            long mid = (lo + hi) >> 1;
            long s = 0;
            boolean fail = false;
            for (int i = 0; i < n; i++) {
                if(mid - a[i] < 0) {
                    fail = true;
                    break;
                }
                s += mid - a[i];
            }
            if(s < mid) fail = true;
            if(fail) lo = mid + 1;
            else {
                ans = mid;
                hi = mid - 1;
            }
        }
        out.printLine(ans);
    }
}
