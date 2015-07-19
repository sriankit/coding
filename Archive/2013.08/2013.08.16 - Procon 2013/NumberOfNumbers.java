package Tasks;

import javaUtils.InReader;
import javaUtils.IntegerUtils;
import javaUtils.OutputWriter;

import java.util.Arrays;

public class NumberOfNumbers {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int[] primes = IntegerUtils.generatePrimes(38);
        int[] a = new int[10005];
        for (int i = 1; i < 10005; i++) {
            String sum = String.valueOf(i);
            int s = 0;
            for (int j = 0; j < sum.length(); j++) {
                s += Character.digit(sum.charAt(j), 10);
            }
            if (Arrays.binarySearch(primes, s) >= 0) a[i] = a[i - 1] + 1;
            else a[i] = a[i - 1];
        }
        int t = in.readInt();
        while (t-- > 0) {
            int l = in.readInt();
            int u = in.readInt();
            out.printLine(a[u] - a[l - 1]);
        }
    }
}
