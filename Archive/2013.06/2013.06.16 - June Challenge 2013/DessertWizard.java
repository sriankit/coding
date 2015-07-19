package Tasks;


import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

public class DessertWizard {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        testNumber = in.readInt();
        while (testNumber-- > 0) {
            int n = in.readInt();
            int[] arr = IOUtils.readIntArray(in, n);
            long[] maxSumPre = new long[n];
            long[] minSumPre = new long[n];

            maxSumPre[0] = arr[0];
            minSumPre[0] = arr[0];
            long maxSoFar = arr[0], maxEndingHere = arr[0];
            long minSoFar = -arr[0], minEndingHere = -arr[0];
            for (int i = 1; i < n; i++) {
                if (maxEndingHere < 0) maxEndingHere = arr[i];
                else maxEndingHere += arr[i];
                if (minEndingHere < 0) minEndingHere = -arr[i];
                else minEndingHere += -arr[i];
                if (maxEndingHere > maxSoFar) maxSoFar = maxEndingHere;
                if (minEndingHere > minSoFar) minSoFar = minEndingHere;
                maxSumPre[i] = maxSoFar;
                minSumPre[i] = -minSoFar;
            }

            long[] maxSumSuf = new long[n];
            long[] minSumSuf = new long[n];

            maxSumSuf[n - 1] = arr[n - 1];
            minSumSuf[n - 1] = arr[n - 1];
            maxSoFar = arr[n - 1];
            maxEndingHere = arr[n - 1];
            minSoFar = -arr[n - 1];
            minEndingHere = -arr[n - 1];

            for (int i = n - 2; i >= 0; i--) {
                if (maxEndingHere < 0) maxEndingHere = arr[i];
                else maxEndingHere += arr[i];
                if (minEndingHere < 0) minEndingHere = -arr[i];
                else minEndingHere += -arr[i];
                if (maxEndingHere > maxSoFar) maxSoFar = maxEndingHere;
                if (minEndingHere > minSoFar) minSoFar = minEndingHere;

                maxSumSuf[i] = maxSoFar;
                minSumSuf[i] = -minSoFar;
            }

            /*System.out.println(Arrays.toString(maxSumPre));
            System.out.println(Arrays.toString(minSumPre));
            System.out.println(Arrays.toString(maxSumSuf));
            System.out.println(Arrays.toString(minSumSuf));*/

            long ans = Long.MIN_VALUE;
            for (int i = 0; i < n - 1; i++) {
                ans = Math.max(ans, Math.abs(maxSumPre[i] - minSumSuf[i + 1]));
                ans = Math.max(ans, Math.abs(minSumPre[i] - maxSumSuf[i + 1]));
            }
            out.printLine(ans);
        }
    }
}
