package Tasks;

import java.math.BigInteger;
import java.util.HashMap;

public class TheNumberGameDivOne {
    HashMap<Long, Boolean> map = new HashMap<Long, Boolean>();

    boolean isWinning(long n) {
        BigInteger b = BigInteger.valueOf(n);
        if (b.isProbablePrime(10)) return false;
        if (map.containsKey(n)) return map.get(n);
        boolean res = true;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                res &= isWinning(n - i);
                res &= isWinning(n - n / i);
            }
        }
        if (res) map.put(n, false);
        else map.put(n, true);
        //System.err.println("return " + !res + " for " + n);
        return !res;
    }

    public String find(long n) {
        if (n == 1) return "Brus";
        System.out.println(isWinning(4));
        System.out.println(isWinning(8));
        if ((n & (n - 1)) == 0) return Long.numberOfTrailingZeros(n) % 2 == 1 ? "Brus" : "John";
        if (n % 2 == 0) return "John";
        else return "Brus";
    }
}
