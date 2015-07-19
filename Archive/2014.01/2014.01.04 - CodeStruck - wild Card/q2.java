package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

public class q2 {
    boolean chk(int n) {
        int d = 0;
        int r = n;
        while (r > 0) {
            r /= 10;
            d++;
        }
        String s = "" + n;
        int cn = 0;
        for (int i = 0; i < d; i++) {
            for (int j = i + 1; j <= d; j++) {
                int num = Integer.parseInt(s.substring(i, j));
                //System.out.println(num);
                if (num % d == 0) {
                    //System.out.println(s.substring(i, j));
                    cn++;
                    if (cn > 1)
                        return false;
                }
            }
        }
        if (cn == 1) return true;
        else return false;
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        int cnt = 0;
        for (int i = 1; i < 100000000; i++) {
            if (chk(i)) cnt++;
        }
        System.out.println(cnt);

    }
}
