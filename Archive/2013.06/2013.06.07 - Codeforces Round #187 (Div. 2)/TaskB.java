package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

public class TaskB {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int m = in.readInt();
        long[] arr = new long[n];
        for (int i = 0; i < n; i++) {
            arr[i] = in.readLong();
        }
        long sum = 0;
        while (m-- > 0) {
            int ch = in.readInt();
            if (ch == 1) {
                int ind = in.readInt() - 1;
                int val = in.readInt();
                arr[ind] = val - sum;
            } else if (ch == 2) {
                sum += in.readInt();
            } else {
                int q = in.readInt() - 1;
                out.printLine(arr[q] + sum);
            }
        }
    }
}
