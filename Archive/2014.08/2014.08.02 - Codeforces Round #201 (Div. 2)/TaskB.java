package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

public class TaskB {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int[] a = IOUtils.readIntArray(in, n);
        int[] ord = new int[n];
        for (int i = 0; i < n; i++) {
            ord[i] = i;
        }
        int ans = 0;
        for (int i = 0; i < n; i++) {
            if(a[i] == i) ans ++;
        }
        boolean inc = true;
        for (int i = 0; i < n; i++) {
            if(a[i] != i) {
                int ind = a[i];
                if(a[ind] == i && a[a[ind]] == ind) {
                    ans += 2;
                    inc = false;
                    break;
                }
            }
        }
        if(inc && ans < n) ans++;
        out.printLine(ans);
    }
}
