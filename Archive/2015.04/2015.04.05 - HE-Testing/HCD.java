package Tasks;

import javaUtils.InReader;
import javaUtils.Matrix;
import javaUtils.OutputWriter;
import javaUtils.RecurrenceSolver;

public class HCD {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        final int MOD = 1000000007;
        long n = in.readLong();
        assert(1 <= n && n <= 1e16);
        RecurrenceSolver solver = new RecurrenceSolver();
        Matrix base = new Matrix(2, 2);
        base.data[0][0] = base.data[0][1] = base.data[1][0] = 1;
        solver.setTransformationMatrix(base);
        solver.setValueMatrix(2, 0);
        Matrix M = solver.getNthMatrix(n, MOD);
        out.printLine((M.data[0][0]));
    }
}
