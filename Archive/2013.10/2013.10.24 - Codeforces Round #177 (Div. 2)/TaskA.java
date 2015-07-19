package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

public class TaskA {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int k = in.readInt();
        long sum = 0;
        for (int i = 0; i < n; i++) {
            int l = in.readInt();
            int r = in.readInt();
            sum += r - l + 1;
        }
        out.printLine((k - sum % k) % k);
    }
}
