package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.Arrays;

public class VerticalSticks {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        testNumber = in.readInt();
        while (testNumber-- > 0) {
            int n = in.readInt();
            int[] arr = IOUtils.readIntArray(in, n);
            Arrays.sort(arr);
            double ans = 0.00;
            for (int i = 0; i < n; i++) {
                int streak = 1;
                int numgrt = n - i;
                while (i + 1 < n && arr[i] == arr[i + 1]) {
                    streak++;
                    i++;
                }
                //System.out.println("numgrt = " + numgrt + " for num = " + arr[i]);
                ans += streak * (n + 1.0) / (numgrt + 1.0);

            }
            out.printf("%.02f\n", ans);
        }
    }
}
