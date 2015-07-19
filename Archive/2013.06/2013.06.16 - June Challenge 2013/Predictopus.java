package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

public class Predictopus {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        testNumber = in.readInt();
        while (testNumber-- > 0) {
            double p = in.readDouble();
            out.printLine(2 * p * (1 - p) * 10000 + Math.max(p * 10000, (1 - p) * 10000));
        }
    }
}
