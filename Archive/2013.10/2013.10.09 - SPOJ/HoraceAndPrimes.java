package Tasks;

import javaUtils.CollectionUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.ArrayList;
import java.util.Arrays;

public class HoraceAndPrimes {

    int limit = 1000006;
    ArrayList<Integer>[] ans = new ArrayList[limit];
    int[] dp = new int[limit];
    int sieve[] = new int[limit];

    public void solve(int testNumber, InReader in, OutputWriter out) {
        sieve[0] = 0;
        sieve[1] = 1;
        sieve[2] = 0;
        for (int i = 2; i < limit; i++) {
            if (sieve[i] == 0) {
                for (int j = 2 * i; j < limit; j += i) {
                    sieve[j] += i;
                }
            }
        }

        //System.out.println(Arrays.toString(Arrays.copyOfRange(sieve, 0, 20)));

        for (int i = 0; i < limit; i++) {
            ans[i] = new ArrayList<Integer>();
        }

        for (int i = 2; i < limit; i++) {
            if (sieve[i] == 0) {
                ans[1].add(i);
                dp[i] = 1;
            } else {
                ans[1 + dp[sieve[i]]].add(i);
                dp[i] = 1 + dp[sieve[i]];
            }
        }

        int q = in.readInt();
        while (q-- > 0) {
            int a = in.readInt();
            int b = in.readInt();
            int k = in.readInt();
            int[] temp = CollectionUtils.toArray(ans[k]);
            int inda = Arrays.binarySearch(temp, a);
            int less, more;
            if (inda < 0) less = -inda - 1;
            else less = inda;
            int indb = Arrays.binarySearch(temp, b);
            if (indb < 0) more = -indb - 1;
            else more = indb + 1;
            out.printLine(more - less);
        }
    }
}
