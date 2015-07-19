package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;
import javaUtils.RecurrenceSolver;

import java.util.Arrays;

public class q5 {

    RecurrenceSolver solver = new RecurrenceSolver();
    long MOD = (long) 1e9 + 7;

    long find(long num) {
        num %= MOD - 1;
        return solver.getNthTerm(num - 1, MOD);
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        //long nCr[][] = IntegerUtils.generateBinomialCoefficients(100005, 1000000007);
        solver.setValueMatrix(2, 3, 4);
        solver.setTransformationMatrix(1, 1, 1);
        System.out.println(Arrays.deepToString(solver.getTransformationMatrix().data));
        System.out.println(Arrays.deepToString(solver.getValueMatrix().data));
        for (int i = 1; i < 15; i++) {
            out.printLine(i, find(i));
            //out.printLine(i, solver.getNthTerm((long)1e15, 1000000007));
        }
        out.printLine("ans : " + find((long) 1e15));

    }
}
