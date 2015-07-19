package Tasks;

import javaUtils.*;

import java.math.BigInteger;
import java.util.ArrayList;

public class DECORATE {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        String s = in.readString();
        int n = s.length();
        int k = in.readInt();

        Manacher palindromesOfS = new Manacher(s);
        int[] sa = SuffixArray.suffixArray(s);
        int[] lcp = new int[n];
        System.arraycopy(SuffixArray.lcp(sa, s), 0, lcp, 1, n - 1);

        ArrayList<Integer> minIndex[] = new ArrayList[2 * n - 1];

        for (int i = 0; i < 2 * n - 1; i++) {
            minIndex[i] = new ArrayList<Integer>();
        }

        for (int i = 0; i < n; i++) {
            int ind = 2 * sa[i] + (lcp[i] == 0 ? 0 : (2 * lcp[i] - 1) / 2 + 1);
            //System.out.println(sa[i]);
            minIndex[ind].add(i);
        }

        /*for (int i = 0; i < 2 * n - 1; i++) {
            System.out.print(i + ": ");
            System.out.println(minIndex[i]);
        } */

        long ans = 0;
        BIT tree = new BIT(2 * n);
        //System.out.println(Arrays.toString(palindromesOfS.p));
        for (int i = 2 * n - 1 - 1; i >= 0; i--) {
            int least = i - palindromesOfS.p[i + 2] + 1;
            tree.update(least + 1, 1);
            for (int ind : minIndex[i]) {
                long contribution = tree.freqTo(2 * sa[ind] + 1);
                //System.out.println(sa[ind] + " contributes " + contribution);
                ans += contribution;
            }
        }
        //System.out.println(ans);

        BigInteger res;
        BigInteger k2 = BigInteger.valueOf((long) k / 2);
        BigInteger k1 = BigInteger.valueOf((long) k);
        BigInteger n1 = BigInteger.valueOf(ans);


        if (k % 2 == 0) {
            res = k2.multiply(n1.add(BigInteger.ONE));
            res = res.multiply(n1.pow(k / 2));

            for (int p = 1; p <= k; p++) {
                res = res.add(n1.pow((int) IntegerUtils.gcd(p, k)));
            }
            out.printLine(res.divide(BigInteger.valueOf((long) k * 2)));

        } else {
            res = k1.multiply(n1.pow(k + 1 >> 1));

            for (int p = 1; p <= k; p++) {
                res = res.add(n1.pow((int) IntegerUtils.gcd(p, k)));
            }
            out.printLine(res.divide(BigInteger.valueOf((long) k * 2)));
        }

    }
}
