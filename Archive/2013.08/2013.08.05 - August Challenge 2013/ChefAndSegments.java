package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.IntegerUtils;
import javaUtils.OutputWriter;

public class ChefAndSegments {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int[] arr = IOUtils.readIntArray(in, n);
        int[] primes = IntegerUtils.generatePrimes(101);
        int primeCount = primes.length;
        int[][] count = new int[n][primeCount];

        for (int i = 0; i < n; i++) {
            int num = arr[i];
            if (i == 0) {
                for (int i1 = 0; i1 < primes.length; i1++) {
                    int prime = primes[i1];
                    int c;
                    for (c = 0; num % prime == 0; c++) {
                        num /= prime;
                    }
                    count[0][i1] = c;
                }
            } else {
                System.arraycopy(count[i - 1], 0, count[i], 0, primeCount);
                for (int i1 = 0; i1 < primes.length; i1++) {
                    int prime = primes[i1];
                    int c;
                    for (c = 0; num % prime == 0; c++) {
                        num /= prime;
                    }
                    count[i][i1] += c;
                }
            }
        }

        int queryCount = in.readInt();
        while (queryCount-- > 0) {
            int l = in.readInt() - 1;
            int r = in.readInt() - 1;
            int mod = in.readInt();
            long ans = 1;
            for (int i = 0; i < primeCount; i++) {
                int c = count[r][i] - (l == 0 ? 0 : count[l - 1][i]);
                if (c == 0) continue;
                long mul = IntegerUtils.power(primes[i], c, mod);
                ans *= mul;
                if (ans >= mod) ans %= mod;
            }
            out.printLine(ans % mod);
        }
    }
}
