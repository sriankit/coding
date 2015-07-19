package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.Arrays;

public class TEAM2 {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        try {
            int[] a = IOUtils.readIntArray(in, 4);
            Arrays.sort(a);
            out.printLine("Case " + testNumber + ": " + (a[2] + a[3]));
        } catch (Exception e) {
            throw new UnknownError();
        }
    }
}
