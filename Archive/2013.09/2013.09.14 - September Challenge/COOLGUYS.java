package Tasks;

import javaUtils.InReader;
import javaUtils.IntegerUtils;
import javaUtils.OutputWriter;

public class COOLGUYS {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        testNumber = in.readInt();
        while (testNumber-- > 0) {
            int n = in.readInt();
            long hi = n;
            long ans = 0;
            for (long i = 1; i <= hi; i++) {
                long k = n / i;
                ans += k;
                ans += (hi - k) * (i - 1);
                if (k <= i) ans -= (i - k) * (i - 1);
                hi = k;
            }

            Fraction fraction = new Fraction(ans, ((long) n * n));
            fraction.standardize();
            out.printLine(fraction);
        }
    }
}

class Fraction {
    long num, den;

    Fraction(long num, long den) {
        this.den = den;
        this.num = num;
    }

    void standardize() {
        long g = IntegerUtils.gcd(num, den);
        num /= g;
        den /= g;
    }

    @Override
    public String toString() {
        return num + "/" + den;
    }
}

