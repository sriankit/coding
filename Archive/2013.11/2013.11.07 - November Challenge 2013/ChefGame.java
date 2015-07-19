package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.IntegerUtils;
import javaUtils.OutputWriter;

import java.util.SortedSet;
import java.util.TreeSet;

public class ChefGame {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        IntegerUtils.generateBinomialCoefficients()
        testNumber = in.readInt();
        while (testNumber-- > 0) {
            int n = in.readInt();
            long s = 0;
            for (int i = 0; i < n; i++) {
                int ni = in.readInt();
                int[] piles = IOUtils.readIntArray(in, ni);
                SortedSet<Integer> set = new TreeSet<Integer>();
                for (int num : piles) set.add(num);
                long MAX = 1L << 46;
                long DIV = 1;
                boolean f = false;
                int pre = set.first() & 1;
                for (int num : set) {
                    if ((num & 1) != (pre)) {
                        f = true;
                    }
                    if (f) DIV <<= 1;
                    if ((num & 1) > 0) s += MAX / DIV;
                    else s -= MAX / DIV;
                }
            }
            if (s > 0) out.printLine("ODD");
            else if (s < 0) out.printLine("EVEN");
            else out.printLine("DON'T PLAY");
        }
    }
}
