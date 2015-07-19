package javaUtils;

public class Fraction {
    public static final Fraction ZERO = new Fraction(0, 1);
    public static final Fraction ONE = new Fraction(1, 1);
    public long numerator, denominator;

    public Fraction(long numerator, long denominator) {
        this.denominator = denominator;
        this.numerator = numerator;
    }

    public Fraction(long numerator) {
        this.numerator = numerator;
        this.denominator = 1;
    }

    public static Fraction add(Fraction a, Fraction b) {
        long lcm = IntegerUtils.lcm(a.denominator, b.denominator);
        Fraction f = new Fraction(a.numerator * lcm / a.denominator + b.numerator * lcm / b.denominator, lcm);
        f.standardize();
        return f;
    }

    public static Fraction subtract(Fraction a, Fraction b) {
        long lcm = IntegerUtils.lcm(a.denominator, b.denominator);
        Fraction f = new Fraction(a.numerator * lcm / a.denominator - b.numerator * lcm / b.denominator, lcm);
        f.standardize();
        return f;
    }

    public static Fraction multiply(Fraction a, Fraction b) {
        if (a.numerator == 0 || b.numerator == 0) return ZERO;
        Fraction frac = new Fraction(a.numerator * b.numerator, a.denominator * b.denominator);
        frac.standardize();
        return frac;
    }

    public static Fraction divide(Fraction a, Fraction b) {
        return multiply(a, Fraction.reciprocal(b));
    }

    public static Fraction reciprocal(Fraction a) {
        a.numerator = a.denominator + (a.denominator = a.numerator) * 0;
        return a;
    }

    public static boolean gt(Fraction a, Fraction b) {
        double fa = a.numerator / (a.denominator + .0);
        double fb = b.numerator / (b.denominator + .0);
        return fa - fb > 1e-8;
    }

    public void standardize() {
        if (numerator == 0) return;
        assert (numerator > 0 && denominator > 0);
        long g = IntegerUtils.gcd(numerator, denominator);
        numerator /= g;
        denominator /= g;
    }

    @Override
    public String toString() {
        return "" + numerator + "/" + denominator;
    }
}
