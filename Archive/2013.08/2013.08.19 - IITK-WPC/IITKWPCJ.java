package Tasks;

import javaUtils.InReader;
import javaUtils.IntegerUtils;
import javaUtils.OutputWriter;

public class IITKWPCJ {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        while (n-- > 0) {
            String s1 = in.readString();
            String s2 = in.readString();
            int l1 = s1.length();
            int l2 = s2.length();
            int g = (int) IntegerUtils.gcd(l1, l2);
            if (l1 < l2) {
                String t = s1;
                s1 = s2;
                s2 = t;

                l1 = l2 + (l2 = l1) * 0;
            }
            int i;
            for (i = 0; i < l2; i++) if (s1.charAt(i) != s2.charAt(i)) break;
            if (i < l2) {
                out.printLine("NO");
                continue;
            }
            for (i = l2 - 1; i >= 0; i--) {
                if (s1.charAt(i) != s2.charAt(i)) break;
            }
            if (i >= 0) {
                out.printLine("NO");
                continue;
            }
            int j;
            for (j = g; j < l1; j++) {
                int k = j - (j / g) * g;
                if (s1.charAt(k) != s1.charAt(j)) break;
            }
            if (j < l1) {
                out.printLine("NO");
                continue;
            }
            for (j = g; j < l2; j++) {
                int k = j - (j / g) * g;
                if (s2.charAt(k) != s2.charAt(j)) break;
            }
            if (j < l2) {
                out.printLine("NO");
                continue;
            }
            out.printLine("YES");

        }

    }
}
