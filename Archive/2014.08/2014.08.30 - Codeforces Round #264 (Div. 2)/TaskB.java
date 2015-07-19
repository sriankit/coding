package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.IntegerUtils;
import javaUtils.OutputWriter;

public class TaskB {
    boolean okay(int[] a) {
        int e = -a[0];
        for (int i = 1; i < a.length; i++) {
            e += a[i - 1] - a[i];
            if(e < 0) return false;
        }
        return e >=0 ;
    }
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int[] a = IOUtils.readIntArray(in, n);
        int e = -a[0];
        int ans = -a[0];
        for (int i = 1; i < n; i++) {
            e += a[i - 1] - a[i];
            ans = Math.min(ans, e);
        }
        out.printLine(- ans);
    }
}
