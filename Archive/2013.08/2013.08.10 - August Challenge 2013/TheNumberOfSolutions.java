package Tasks;

import javaUtils.InReader;
import javaUtils.IntegerUtils;
import javaUtils.OutputWriter;

public class TheNumberOfSolutions {
    long MOD = 1000000007;

    public void solve(int testNumber, InReader in, OutputWriter out) {
        testNumber = in.readInt();
        while (testNumber-- > 0) {
            int upper = in.readInt();
            int d = in.readInt();
            int m = in.readInt();
            int N = in.readInt();

            long[] cnt = new long[N];
            for (int i = 0; i < Math.min(N, upper + 1); i++) {
                int exp = (int) IntegerUtils.power(i, d, N);
                cnt[exp] += (upper - i) / N + 1;
                //assert(cnt[exp] >= 0);
            }
            //System.out.println("0 ^ 0 = " + IntegerUtils.power(0, 0, 1));

            long ans = 0;

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    for (int k = 0; k < N; k++) {
                        if ((i + j + k) % N == m) {
                            long mul = (cnt[i] * cnt[j]) % MOD;
                            mul = (mul * cnt[k]) % MOD;
                            ans += mul;
                            if (ans >= MOD) ans -= MOD;
                        }
                    }
                }
            }
            out.printLine(ans % MOD);

        }
    }
}
