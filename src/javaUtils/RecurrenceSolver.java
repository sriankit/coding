package javaUtils;

public class RecurrenceSolver {
    Matrix T, C;
    int setVals;

    public Matrix getTransformationMatrix() {
        return T;
    }

    public void setTransformationMatrix(Matrix T) {
        this.T = T;
    }

    public Matrix getValueMatrix() {
        return C;
    }

    public void setValueMatrix(long... args) {
        setVals = args.length;
        C = new Matrix(setVals, 1);
        for (int i = 0; i < setVals; i++) {
            C.data[i][0] = args[i];
        }
    }

    public Matrix getNthMatrix(long n, int MOD) {
        return Matrix.multiply(T.power(n - 1, MOD), C, MOD);
    }
}
