package Tasks;

import javaUtils.InReader;
import javaUtils.IntegerUtils;
import javaUtils.OutputWriter;

import java.util.Arrays;

public class FarmerFeb {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int[] primes = IntegerUtils.generatePrimes(100005);
        testNumber = in.readInt();
        while (testNumber-- > 0) {
            int x = in.readInt();
            int y = in.readInt();
            int s = x + y;
            int k = Arrays.binarySearch(primes, s);
            if (k < 0) k = -(k + 1);
            for (int idx = k; idx < primes.length; idx++) {
                if (primes[idx] - s > 0) {
                    out.printLine(primes[idx] - s);
                    break;
                }
            }
        }
    }
}
