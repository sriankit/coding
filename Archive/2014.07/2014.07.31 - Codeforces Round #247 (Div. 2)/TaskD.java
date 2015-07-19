package Tasks;

import javaUtils.InReader;
import javaUtils.IntegerUtils;
import javaUtils.OutputWriter;

public class TaskD {

    long[][] C = IntegerUtils.generateBinomialCoefficients(64);

    long countWithKOnes(int k, long lim) {
        long ans = Long.bitCount(lim) == k ? 1 : 0;
        for (int bit = 63; bit >= 1; bit--) {
            if ((lim & (1L << bit)) > 0) {
                if (k == 0) {
                    ans++;
                    break;
                } else ans += C[bit - 1][k];
                --k;
            }
        }
        return ans;
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        long numCount = in.readLong();
        int oneBits = in.readInt();
        long lo = 1, hi = (long) (1e18);
        long ans = hi;
        while (lo <= hi) {
            long mid = lo + hi >> 1;
            if (countWithKOnes(oneBits, 2 * mid) - countWithKOnes(oneBits, mid) == numCount) {
                ans = mid;
                hi = mid - 1;
            } else lo = mid + 1;
        }
        out.printLine(ans);
    }
}
