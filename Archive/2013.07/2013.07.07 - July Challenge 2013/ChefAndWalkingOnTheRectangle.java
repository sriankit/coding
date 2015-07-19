package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

public class ChefAndWalkingOnTheRectangle {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int m = in.readInt();
        int k = in.readInt();
        if (n == 1 && m == 1) out.printLine(0);
        else if (n == 1 || m == 1) {
            if (Math.abs(n - m) == 1) out.printLine(0);
            else out.printLine(k);
        } else out.printLine((k + 1) / 2);
    }
}
