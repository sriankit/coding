package Tasks;

import javaUtils.InReader;
import javaUtils.IntegerUtils;
import javaUtils.OutputWriter;

import java.util.Arrays;

public class GCDGame {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        testNumber = in.readInt();
        boolean[] isPrime = IntegerUtils.generatePrimalityTable(100005);
        int[] ok = new int[100005];
        int[] cnt = new int[100005];
        Arrays.fill(ok, 1);
        int lim = 100005;
        for (int i = 2; i < lim; i++) {
            if (isPrime[i])
                for (int j = i; j < lim; j += i) {
                    ok[j] *= i;
                    cnt[j]++;
                }
        }
        /*for (int i = 2; i < 30; i++) {
            System.out.print(i + ": " + ok[i] + " " + cnt[i] + ", ");
        } */
        while (testNumber-- > 0) {
            int a = in.readInt();
            int b = in.readInt();
            if (b < a) a = b + (b = a) * 0;
            long ans = 0;
            for (int i = 2; i <= a; i++) {
                if (i == ok[i]) {
                    int c = cnt[i];
                    int sgn = -1;
                    if (c % 2 == 0) sgn = 1;

                    ans += sgn * (b / i) * (a / i);
                }
            }
            out.printLine(a * b + ans);
        }
    }
}
