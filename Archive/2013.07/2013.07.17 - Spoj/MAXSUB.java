package Tasks;

import javaUtils.*;

import java.util.Arrays;

public class MAXSUB {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        testNumber = in.readInt();
        while (testNumber-- > 0) {
            int n;
            n = in.readInt();
            int[] arr = IOUtils.readIntArray(in, n);
            Arrays.sort(arr);
            int cnt0 = ArrayUtils.count(arr, 0);
            long sum = 0;
            for (int i = arr.length - 1; i >= 0; i--) {
                int i1 = arr[i];
                if (i1 > 0) sum += i1;
                else break;
            }
            if (sum == 0) {
                int last = arr[arr.length - 1];
                if (last < 0) out.printLine(arr[arr.length - 1], ArrayUtils.count(arr, arr[arr.length - 1]));
                else out.printLine(0, IntegerUtils.power(2, cnt0, 1000000009) - 1);
            } else {
                out.printLine(sum, IntegerUtils.power(2, cnt0, 1000000009));
            }
        }
    }
}
