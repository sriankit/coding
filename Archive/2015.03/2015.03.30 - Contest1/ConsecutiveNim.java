package Tasks;

import javaUtils.*;

import java.util.Arrays;

public class ConsecutiveNim {
    final long MOD = (long)1e9 + 7;
    final int MAXN = (int) 1e5 + 5;

    long[] fact, invfact;
    int[] arr;

    public ConsecutiveNim() {
        fact = IntegerUtils.generateFactorial(MAXN, MOD);
        invfact = IntegerUtils.generateReverseFactorials(MAXN, MOD);
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        arr = IOUtils.readIntArray(in, n);
        Arrays.sort(arr);

        /*
        This fragment computes the product of inverses factorials for the run-lengths of all integers != 1
        For e.g. in case of 1 1 2 3 3
        others = invfact[1] * invfact[2]
        Here, we consider number of occurences of 2 = 1
        number of occurences of 3 = 2
        Note that 1 is not being counted.
         */

        long others = 1;
        int ones = 0;
        int run = 0;
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] == 1) {
                ones ++;
                continue;
            }
            run ++;
            if(i + 1 == arr.length || arr[i] != arr[i + 1]) {
                others *= invfact[run];
                if(others >= MOD) others %= MOD;
                run = 0;
            }
        }

        /*
        Special case. After skipping over all ones, there are no more stone piles to actually play on
         */

        if(ones == n) {
            if(n % 2 == 0) out.printLine(0);
            else out.printLine(1);
            return;
        }

        /*
        This fragment computes answer using the principle of inclusion-exclusion.
        ans = P(0) - P(1) + P(2) - P(3).. and so on
        where, P(x) is the number of permutations of length n having prefix of 1's with length >= x
        */

        long ans = 0;
        int sgn = 1;
        for (int one = 0; one <= ones; one++) {
            long perms = others * (fact[n - one] * invfact[ones - one] % MOD) % MOD;
            ans += sgn * perms;
            if (ans >= MOD) ans -= MOD;
            if (ans < 0) ans += MOD;
            sgn *= -1;
        }
        assert(ans >= 0 && ans < MOD);
        out.printLine(ans);
    }
}
