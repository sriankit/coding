package Tasks;

import javaUtils.InReader;
import javaUtils.IntegerUtils;
import javaUtils.OutputWriter;

public class Matrix {
    long MOD = (int) 1e9 + 7;
    long[] fac = IntegerUtils.generateFactorial(2000006, MOD);
    long[] invFac = IntegerUtils.generateReverseFactorials(2000006, MOD);

    public void solve(int testNumber, InReader in, OutputWriter out) {
        int t = in.readInt();
        while (t-- > 0) {
            int n = in.readInt() - 1;
            int m = in.readInt() - 1;
            out.printLine((fac[n + m] * (invFac[n] * invFac[m] % MOD) % MOD));
        }


    }
}
