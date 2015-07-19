package Tasks;

import javaUtils.Fraction;

public class Champagne {

    int top, h;
    Fraction DEFAULT = new Fraction(2);
    Fraction HALF = new Fraction(1, 2);
    Fraction store[];

    void go(int i, int j, Fraction cap) {
        if (j > h) return;
        Fraction frac = Fraction.add(store[i], cap);
        if (frac.numerator > 2 * frac.denominator) {
            cap = Fraction.subtract(cap, Fraction.subtract(DEFAULT, store[i]));
            store[i] = DEFAULT;
            Fraction pass = new Fraction(cap.numerator, cap.denominator * 2);
            pass.standardize();
            go(i + j, j + 1, pass);
            go(i + j + 1, j + 1, pass);
        } else store[i] = Fraction.add(cap, store[i]);
    }

    public String howMuch(int height, int glass, int units) {
        top = units;
        h = height;
        store = new Fraction[2500];
        Fraction pass = new Fraction(units);

        for (int i = 0; i < store.length; i++) {
            store[i] = Fraction.ZERO;
        }

        go(1, 1, pass);

        if (store[glass] != null) {
            store[glass] = Fraction.multiply(store[glass], HALF);
        } else return Fraction.ZERO.toString();
        return store[glass].toString();
    }


}
