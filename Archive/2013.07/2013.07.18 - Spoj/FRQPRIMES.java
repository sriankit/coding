package Tasks;

import javaUtils.InReader;
import javaUtils.IntegerUtils;
import javaUtils.OutputWriter;

public class FRQPRIMES {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int[] primes = IntegerUtils.generatePrimes(100005);
        //System.out.println(Arrays.toString(primes));
        testNumber = in.readInt();
        while (testNumber-- > 0) {
            int n, k;
            n = in.readInt();
            k = in.readInt();
            long tot = n - 2 + 1;
            if (k == 0) {
                out.printLine(tot * (tot + 1) / 2);
                continue;
            }
            long ans = 0;
            for (int i = 0; i < primes.length; i++) {
                long sts = i == 0 ? 1 : primes[i] - primes[i - 1];
                long ends = 0;
                if (i + k - 1 < primes.length) ends = n + 1 - primes[i + k - 1];
                if (ends <= 0) break;
                ans += sts * ends;
            }
            out.printLine(ans);
            //System.err.println("");
        }
    }
}
