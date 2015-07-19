package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

public class Number {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        long[] ans = new long[501];
        for (int i = 1; i < 501; i++) {
            long num = 0;
            long p10 = 1;
            for (int k = 1; ; k++) {
                num = 0;
                int mask = k;
                p10 = 1;
                while (mask > 0) {
                    num += (mask & 1) * 9 * p10;
                    mask >>= 1;
                    p10 *= 10;
                }
                if (num % i == 0) {
                    break;
                }
                //if(num > 0) System.out.println(num);
            }
            ans[i] = num;
        }
        int t = in.readInt();
        while (t-- > 0) {
            out.printLine(ans[in.readInt()]);
        }
    }
}
