package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

public class TaskA {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int[] a = IOUtils.readIntArray(in, 4);
        String s = in.readString();
        int total = 0;
        for (int i = 0; i < s.length(); i++) {
            total += a[s.charAt(i) - '1'];
        }
        out.printLine(total);
    }
}
