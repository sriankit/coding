package Tasks;

import java.util.Arrays;
import java.util.HashMap;

public class TheCoffeeTimeDivOne {
    public long find(int n, int[] tea) {
        Arrays.sort(tea);
        int rytc = -1;
        int rytt = -1;
        int cntc = 0;
        int cntt = 0;
        long res = 4 * n;
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int c : tea) map.put(c, -1);
        while (n >= 1) {
            if (map.containsKey(n)) {
                cntt++;
                rytt = Math.max(rytt, n);
                if (cntt == 7) {
                    res += 47 + 2 * rytt;
                    rytt = -1;
                    cntt = 0;
                }
            } else {
                cntc++;
                rytc = Math.max(rytc, n);
                if (cntc == 7) {
                    res += 47 + 2 * rytc;
                    rytc = -1;
                    cntc = 0;
                }
            }
            n--;
        }

        //System.out.println(cntt);
        //
        // System.out.println("cntc = " + cntc);
        if (cntt > 0) res += 47 + 2 * rytt;
        if (cntc > 0) res += 47 + 2 * rytc;
        return (res);
    }
}
