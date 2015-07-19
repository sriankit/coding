package Tasks;

import javaUtils.GaussianElimination;
import javaUtils.InReader;
import javaUtils.OutputWriter;

public class NDice {
    double A[][], B[];
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        if(n == 1) {
            out.printLine(1);
            return;
        }
        A = new double[n][n];
        B = new double[n];
        A[0][0] = -1;
        A[0][1] = 1;
        B[0] = -1;
        for(int i = 1;i < n;++i) {
            B[i] = -n;
            for(int j = 1;j < i;++j) {
                A[i][j] = 1;
            }
            A[i][i] = -(n - 1);
            if(i != n - 1)
                A[i][i + 1] = (n - i);
        }
        double[] res = GaussianElimination.lsolve(A, B);
        System.out.print(res[0] + "\n");
        out.printLine(res[0]);
    }
}
