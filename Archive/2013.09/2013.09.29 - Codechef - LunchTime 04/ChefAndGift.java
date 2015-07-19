package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

public class ChefAndGift {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int[] a = IOUtils.readIntArray(in, n);
        long min = a[0], max = a[0];
        for (int i = 1; i < n; i++) {
            long[] arr = new long[]{min + a[i], min - a[i], min * a[i], max + a[i], max - a[i], max * a[i]};
            for (long num : arr) min = Math.min(min, num);
            for (long num : arr) max = Math.max(max, num);

        }
        out.printLine(min);
    }
}
