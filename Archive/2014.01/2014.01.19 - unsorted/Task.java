package Tasks;

import javaUtils.*;

import java.util.Arrays;

public class Task {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int[] a = IOUtils.readIntArray(in, n);
        int lim = a[ArrayUtils.maxPosition(a)];
        long[] p1 = new long[lim + 1];
        long[] p2 = new long[lim + 1];
        for (int i = 0; i < n; i++) {
            p1[a[i]]++;
            p2[lim - a[i]]++;
        }
        long[] res = FastFourierTransform.multiply(p1, p2);
        int cnt = 0;
        for (int i = lim + 1; i <= 2 * lim; i++) {
            if (res[i] > 0) cnt++;
        }
        Arrays.sort(a);
        for (int i = 1; i < a.length; i++) {
            if (a[i] == a[i - 1]) {
                cnt++;
                break;
            }
        }
        out.printLine(cnt);
    }
}
