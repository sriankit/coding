package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

public class Reign {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int t = in.readInt();
        while (t-- > 0) {
            int n = in.readInt();
            int k = in.readInt();

            int[] a = IOUtils.readIntArray(in, n);

            long sum = 0, maxSoFar = Long.MIN_VALUE;
            long maxSum[] = new long[n];
            for (int i = 0; i < n; i++) {
                sum += a[i];
                if (sum < a[i]) sum = a[i];
                maxSoFar = Math.max(maxSoFar, sum);
                maxSum[i] = maxSoFar;
            }

            sum = 0;
            maxSoFar = Long.MIN_VALUE;
            long[] maxSufSum = new long[n];
            for (int i = n - 1; i >= 0; i--) {
                sum += a[i];
                if (sum < a[i]) sum = a[i];
                maxSoFar = Math.max(maxSoFar, sum);
                maxSufSum[i] = maxSoFar;
            }

            long ans = Long.MIN_VALUE;

            for (int i = 0; i + k + 1 < n; i++) {
                ans = Math.max(maxSum[i] + maxSufSum[i + k + 1], ans);
            }
            out.printLine(ans);
        }
    }
}
