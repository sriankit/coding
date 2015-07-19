package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.Arrays;

public class TaskD {
    long intersect(long a, long b, int c, int d) {
        long lmin = Math.max(a, c);
        long lmax = Math.min(b, d);
        if (lmin > lmax) return 0;
        else return lmax - lmin + 1;
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        int a = in.readInt();
        int b = in.readInt();
        int c = in.readInt();
        int d = in.readInt();
        int k = in.readInt();
        long[] allLucky = new long[100000];
        allLucky[0] = 0;
        int luckyCount = 0;
        for (int length = 1; length < 11; length++) {
            int mask = 1 << length;
            for (int i = 0; i < mask; i++) {
                //works in reverse but shouldn't afffect functionality
                long num = 0;
                int lmask = i;
                int bits = length;
                while (bits > 0) {
                    if ((lmask & 1) == 1) num = num * 10 + 7;
                    else num = num * 10 + 4;
                    lmask >>= 1;
                    bits--;
                }
                allLucky[++luckyCount] = num;
            }
        }
        allLucky[++luckyCount] = (long) 1e9;
        Arrays.sort(allLucky, 0, luckyCount);
        //System.out.println(luckyCount);
        //System.out.println(Arrays.toString(allLucky));
        long total = 0;
        long tries = ((long) (b - a + 1)) * (d - c + 1);
        //System.out.println(tries);
        for (int i = 1; i + k < luckyCount; i++) {
            int st = i;
            int end = i + k - 1;
            long frst = intersect(allLucky[st - 1] + 1, allLucky[st], a, b) * intersect(allLucky[end], allLucky[end + 1] - 1, c, d);
            long sec = intersect(allLucky[st - 1] + 1, allLucky[st], c, d) * intersect(allLucky[end], allLucky[end + 1] - 1, a, b);

            total += frst + sec;
            int lmin = Math.max(a, c);
            int lmax = Math.min(b, d);
            if (frst + sec > 0 && k == 1 && intersect(a, b, c, d) > 0) total -= 1;
            //total -= ( intersect(allLucky[end], allLucky[end + 1], lmin, lmax) ) * ( intersect(1, allLucky[st], lmin, lmax) );

        }

        out.printf("%.12f", total * 1.0 / tries);
    }
}
