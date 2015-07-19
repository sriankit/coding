package javaUtils;

public class ZAlgorithm {
    public static int[] getZfunc(String str) {
        int L = 0, R = 0;
        char[] s = str.toCharArray();
        int n = s.length;
        int[] z = new int[n];
        z[0] = n;
        for (int i = 1; i < n; i++) {
            if (i > R) {
                L = R = i;
                while (R < n && s[R - L] == s[R]) R++;
                z[i] = R - L;
                R--;
            } else {
                int k = i - L;
                if (z[k] < R - i + 1) z[i] = z[k];
                else {
                    L = i;
                    while (R < n && s[R - L] == s[R]) R++;
                    z[i] = R - L;
                    R--;
                }
            }
        }
        return z;
    }
}
