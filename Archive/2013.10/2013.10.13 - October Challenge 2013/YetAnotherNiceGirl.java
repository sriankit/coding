package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.Arrays;

public class YetAnotherNiceGirl {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        double a[] = new double[n];
        double b[] = new double[n];
        for (int i = 0; i < n; i++) {
            a[i] = Double.parseDouble(in.next());
            a[i] = Math.log(a[i]) / a[i];
        }
        for (int i = 0; i < n; i++) {
            b[i] = Double.parseDouble(in.next());
            b[i] = Math.log(b[i]) / b[i];
        }
        Arrays.sort(b);

        long add = 0;

        for (double num : a) {

            int ans = -1;
            int lo = 0, hi = n - 1;

            while (hi >= lo) {
                int mid = lo + hi >> 1;
                if (b[mid] >= num) {
                    hi = mid - 1;
                } else {
                    ans = mid;
                    lo = mid + 1;
                }
            }
            add += (ans + 1);
        }
        out.printLine(((double) add) / n);
    }
}
