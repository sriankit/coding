package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.Arrays;

public class MakeHimHappy {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int k = in.readInt();
        int a[] = IOUtils.readIntArray(in, n);
        int lim = (int) 1e6 + 3;
        int left[] = new int[lim];
        int ryt[] = new int[lim];
        Arrays.fill(left, n + 1);
        Arrays.fill(ryt, n + 1);
        for (int i = 0; i < n; i++) {
            left[a[i]] = Math.min(left[a[i]], i + 1);
            ryt[a[i]] = Math.min(ryt[a[i]], n - i);
        }
        int ans = 3 * n;
        for (int i = 1; i < k; i++) {
            int t1 = i;
            int t2 = k - i;
            if (t1 != t2) {
                if (left[t1] != n + 1 && left[t2] != n + 1) {
                    ans = Math.min(ans, Math.max(Math.min(left[t1], ryt[t1]), Math.min(left[t2], ryt[t2])));
                }
            }
        }
        if (ans == 3 * n) out.printLine(-1);
        else out.printLine(ans);
    }
}
