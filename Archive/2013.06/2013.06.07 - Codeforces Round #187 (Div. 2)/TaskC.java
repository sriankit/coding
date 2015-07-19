package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

public class TaskC {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int k = in.readInt();
        long[] arr = new long[n + 1];
        for (int i = 1; i < n + 1; i++) {
            arr[i] = in.readInt();
        }
        long[] sum = new long[n + 1];
        sum[1] = 0;
        int dynum = n;
        int dyi = 2;
        for (int i = 2; i < n + 1; i++) {
            long d = sum[dyi - 1];
            d -= (dynum - dyi) * (arr[i]) * (dyi - 1);
            //sum[i] += arr[i] * (i - 1);
            if (d < k) {
                out.printLine(i);
                --dynum;
                continue;
            }
            sum[dyi] = sum[dyi - 1] + arr[i] * (dyi - 1);
            ++dyi;
        }

    }
}
