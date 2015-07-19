package Tasks;


import javaUtils.InReader;
import javaUtils.IntegerUtils;
import javaUtils.OutputWriter;

public class q6 {
    long MOD = (long) 1e9 + 7;
    long fact[] = IntegerUtils.generateFactorial(100005, MOD);
    long invFact[] = IntegerUtils.generateReverseFactorials(100005, MOD);
    int N;

    long nCr(int n, int r) {
        return (((fact[n] * invFact[r]) % MOD) * invFact[n - r]) % MOD;
    }

    long getViolation(int minA, int minB, int minC) {
        //think u always violate a
        long ans = 0;
        for (int i = 0; i < minA; i++) {
            for (int j = minB; j < 10000 - i; j++) {
                if (N - i - j >= minC) {
                    ans += (nCr(N, i) * nCr(N - i, j)) % MOD;
                    if (ans >= MOD) ans -= MOD;
                }
            }
        }
        return ans;
    }

    long twoViolation(int minA, int minB, int minC) {
        //violating a and b
        long ans = 0;
        for (int i = 0; i < minA; i++) {
            for (int j = 0; j < minB; j++) {
                if (N - i - j >= minC) {
                    ans += (nCr(N, i) * nCr(N - i, j)) % MOD;
                    if (ans >= MOD) ans -= MOD;
                }
            }
        }
        return ans;
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        N = in.readInt();
        int r = in.readInt();
        int g = in.readInt();
        int b = in.readInt();
        long redViolate = getViolation(r, Math.min(g, b), Math.max(g, b));
        long greenViolate = getViolation(g, Math.min(r, g), Math.max(r, g));
        long blueViolate = getViolation(b, Math.min(g, r), Math.max(r, g));

        long redGreenViolate = twoViolation(r, g, b);
        long blueGreenViolate = twoViolation(g, b, r);
        long redBlueViolate = twoViolation(r, b, g);

        long total = IntegerUtils.power(3, N, MOD);

        total -= redViolate;
        if (total < 0) total += MOD;

        total -= greenViolate;
        if (total < 0) total += MOD;

        total -= blueViolate;
        if (total < 0) total += MOD;

        total -= redGreenViolate;
        if (total < 0) total += MOD;

        total -= blueGreenViolate;
        if (total < 0) total += MOD;

        total -= redBlueViolate;
        if (total < 0) total += MOD;

        out.printLine(total);
    }
}

