package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.HashSet;

public class Task {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        HashSet<Long> fibs = new HashSet<Long>();
        long a = 0;
        long b = 1;
        fibs.add(a);
        fibs.add(b);
        while (a + b <= 1e10) {
            long c = a + b;
            fibs.add(c);
            //System.out.println(c);
            a = b;
            b = c;
        }
        int n = in.readInt();
        while (n-- > 0) {
            long q = in.readLong();
            if (fibs.contains(q)) out.printLine("IsFibo");
            else out.printLine("IsNotFibo");
        }
    }
}
