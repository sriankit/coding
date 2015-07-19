package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.Arrays;

public class INTEG {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int[] a = IOUtils.readIntArray(in, n);
        int x = in.readInt();
        Arrays.sort(a);
        int called = 0;
        long cost = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (i < x) {
                if (a[i] + called < 0) cost += -(a[i] + called);
            } else {
                if (i == x) {
                    called = -a[i];
                    cost += called * (long) x;
                }
            }
        }
        out.printLine(cost);

    }
}
