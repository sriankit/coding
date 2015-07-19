package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

public class KGP2 {

    int getCost(int num) {
        int ans = 0, m2 = 0;
        ans += Integer.bitCount(num);
        m2 = Math.max(m2, Integer.toBinaryString(num).length() - 1);
        return Math.max(0, ans + m2 - 1);
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        int t = in.readInt();
        while (t-- > 0) {
            int n = in.readInt();
            int cost = getCost(n);
            String s = Integer.toBinaryString(n);
            //System.out.println("s = " + s);
            int len = s.length();
            int c1 = s.length() - 1;
            int c2 = len - (s.substring(1).indexOf('1') + 1) - 1;
            //System.out.println("c2 = " + c2);
            int ones = Integer.bitCount(n);
            if (ones == 1) {
                out.printLine(getCost(n / 2));
                continue;
            }
            int bitc = ones;

            ones -= 2;
            while (c1 < c2 && ones > 0) {
                c1++;
                ones--;
            }
            while (c2 < c1 && ones > 0) {
                c2++;
                ones--;
            }
            int ans = Math.max(c1, c2) + (ones + 1) / 2;

            int rival = 0;

            if (s.startsWith("10")) {
                c1 = len - 1 + 1 - 2;
                c2 = c1;
                ones = bitc - 1;
                rival = c1 + (ones + 1) / 2;
                ans = Math.min(rival, ans);
            } else {
                c1 = len - 1;
                ones = bitc - 1;
                int gone = 0;
                for (int i = 1; i < len; i++) {
                    if (s.charAt(i) == '1') {
                        int lc = c1 + gone;
                        int lc2 = len - i - 1;
                        int lones = ones - gone;
                        while (lc < lc2 && lones > 0) {
                            lc++;
                            lones--;
                        }
                        while (lc2 < lc && lones > 0) {
                            lc2++;
                            lones--;
                        }
                        rival = Math.max(lc, lc2) + (ones + 1) / 2;
                        ans = Math.min(ans, rival);
                        gone++;
                    } else {
                        if (s.charAt(i - 1) == '1') {
                            int lc = c1 + gone;
                            int lc2 = len - i - 1;
                            int lones = ones - gone;
                            while (lc < lc2 && lones > 0) {
                                lc++;
                                lones--;
                            }
                            while (lc2 < lc && lones > 0) {
                                lc2++;
                                lones--;
                            }
                            rival = Math.max(lc, lc2) + (ones + 1) / 2;
                            ans = Math.min(ans, rival);
                        }
                    }
                }
            }


            out.printLine(ans);

        }
    }
}
