package Tasks;

import javaUtils.InReader;
import javaUtils.IntegerUtils;
import javaUtils.OutputWriter;

import java.util.Arrays;

public class YetAnotherCuteGirl {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int[] primes = IntegerUtils.generatePrimes(1000006);
        int primeCount = primes.length;
        int t = in.readInt();
        while (t-- > 0) {
            long l = in.readLong();
            long r = in.readLong();
            boolean[] take = new boolean[(int) (r - l + 1)];
            boolean[] isPrime = new boolean[(int) (r - l + 1)];
            Arrays.fill(isPrime, true);
            int ret = 0;
            for (long pr : primes) {
                for (int power : primes) {
                    if (power == 2) continue;
                    long num = IntegerUtils.power(pr, power - 1);
                    if (num > r || num < 0) break;
                    if (num >= l && num <= r) take[(int) (num - l)] = true;
                }

                for (long i = pr * (l / pr); i <= r; i += pr) {
                    if (i >= l && i != pr) isPrime[(int) (i - l)] = false;
                }

            }
            for (int i = 0; i < take.length; i++) {
                boolean aTake = take[i];
                if (aTake) {
                    //System.out.println(i + l);
                    ret++;
                }
                if (isPrime[i] && i + l != 1) {
                    //System.out.println("prime = " + (i + l));
                    ret++;
                }
            }
            out.printLine(ret);
        }
    }
}
