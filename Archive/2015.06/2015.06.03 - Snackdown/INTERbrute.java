package Tasks;

import javaUtils.ArrayUtils;
import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

public class INTERbrute {

    int[] a;
    int w;

    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        w = in.readInt();
        a = IOUtils.readIntArray(in, n);
        int last = 0;
        long ans = solveInstance(0, n - 1);
        out.printLine(ans);
    }

    private long solveInstance(int lo, int hi) {
        long res = 0;
        for (int i = lo; i <= hi; i++) {
            for (int j = i + w - 1; j <= hi; j++) {
                int mx = ArrayUtils.maxElement(a, i, j + 1);
                int mn = ArrayUtils.minElement(a, i, j + 1);
                if(mx - mn == j - i) res++;
            }
        }
        return res;
    }
}
