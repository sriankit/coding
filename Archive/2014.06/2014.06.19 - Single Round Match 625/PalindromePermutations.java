package Tasks;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

public class PalindromePermutations {

    BigDecimal fact(long n) {
        BigDecimal res = BigDecimal.ONE;
        for (int i = 1; i <= n; i++) {
            res = res.multiply(BigDecimal.valueOf((long) i));
        }
        return res;
    }

    public double palindromeProbability(String word) {
        int[] occ = new int[26];
        char[] s = word.toCharArray();
        for (char c : s) {
            occ[(int) (c - 'a')]++;
        }
        double facts[] = new double[20];
        facts[0] = 1;
        int lim = 1;
        for (lim = 1; lim < 20; lim++) {
            facts[lim] = lim * facts[lim - 1];
            if (facts[lim] > 1e12) break;
        }
        ArrayList<Integer> sel = new ArrayList<Integer>();
        boolean oddTaken = false;
        for (int i = 0; i < 26; i++) {
            if (occ[i] > 0) {
                if (occ[i] % 2 == 0) sel.add(i);
                else if (!oddTaken) {
                    oddTaken = true;
                    sel.add(i);
                } else return 0D;
            }
        }
        BigInteger ans = BigInteger.ONE;
        BigDecimal ansd = BigDecimal.ONE;
        ansd = ansd.divide(fact(s.length));
        for (int i = 0; i < 26; i++) {
            int c = occ[i];
            ansd = ansd.multiply(fact(c));
            ansd = ansd.divide(fact(c / 2));
        }
        int n = s.length;
        ansd = ansd.multiply(fact(n / 2));

        return ansd.doubleValue();
    }
}
