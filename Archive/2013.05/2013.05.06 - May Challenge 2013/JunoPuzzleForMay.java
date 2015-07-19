package Tasks;

import javaUtils.InReader;
import javaUtils.IntegerUtils;
import javaUtils.OutputWriter;

public class JunoPuzzleForMay {
    long MOD = 1000000007;

    public void solve(int testNumber, InReader in, OutputWriter out) {
        testNumber = in.readInt();
        while (testNumber-- > 0) {
            long v = in.readLong();
            int n = in.readInt();
            long P[], Q[];
            P = new long[n];
            Q = new long[n];
            P[0] = in.readLong();
            P[1] = in.readLong();
            long a0 = in.readLong();
            long b0 = in.readLong();
            long c0 = in.readLong();
            long m0 = in.readLong();
            Q[0] = in.readLong();
            Q[1] = in.readLong();
            long a1 = in.readLong();
            long b1 = in.readLong();
            long c1 = in.readLong();
            long m1 = in.readLong();

            a0 = (a0 * a0) % m0;
            a1 = (a1 * a1) % m1;

            for (int i = 2; i < n; i++) {
                P[i] = ((a0 * P[i - 1]) % m0 + (b0 * P[i - 2]) % m0 + c0 % m0) % m0;
                Q[i] = ((a1 * Q[i - 1]) % m1 + (b1 * Q[i - 2]) % m1 + c1 % m1) % m1;
            }
            long exp = 1;
            v %= MOD;
            boolean flag = false;
            for (int i = 0; i < n; i++) {
                long L = P[i] * m1 + Q[i];              //L = l[i]-1
                if (L == 0) {
                    flag = true;
                }
                L %= MOD - 1;
                exp = (exp * L) % (MOD - 1);
            }
            if (v == 0) {
                if (flag) out.printLine(1);
                else out.printLine(0);
            } else out.printLine(IntegerUtils.power(v, exp, MOD));
        }
    }
}
