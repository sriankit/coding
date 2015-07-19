package javaUtils;

import java.util.Arrays;

public class Matrix {
    public final long[][] data;
    public final int rowCount;
    public final int columnCount;

    public Matrix(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.data = new long[rowCount][columnCount];
    }

    public Matrix(long[][] arr) {
        this.rowCount = arr.length;
        this.columnCount = arr[0].length;
        data = arr.clone();
    }

    public static Matrix multiply(Matrix first, Matrix second) {
        Matrix result = new Matrix(first.rowCount, second.columnCount);
        for (int i = 0; i < first.rowCount; i++) {
            for (int j = 0; j < second.rowCount; j++) {
                for (int k = 0; k < second.columnCount; k++)
                    result.data[i][k] = (result.data[i][k] + (first.data[i][j] * second.data[j][k]));
            }
        }
        return result;
    }

    public static Matrix multiply(Matrix first, Matrix second, int MOD) {
        Matrix result = new Matrix(first.rowCount, second.columnCount);
        for (int i = 0; i < first.rowCount; i++) {
            for (int j = 0; j < second.rowCount; j++) {
                for (int k = 0; k < second.columnCount; k++) {
                    int add = (int)((first.data[i][j] * second.data[j][k]) % MOD);
                    result.data[i][k] += add;
                    if(result.data[i][k] >= MOD) result.data[i][k] -= MOD;
                }
            }
        }
        return result;
    }

    public static Matrix multiply(Matrix first, Matrix second, long MOD) {
        Matrix result = new Matrix(first.rowCount, second.columnCount);
        for (int i = 0; i < first.rowCount; i++) {
            for (int j = 0; j < second.rowCount; j++) {
                for (int k = 0; k < second.columnCount; k++) {
                    int add = (int)((first.data[i][j] * second.data[j][k]) % MOD);
                    result.data[i][k] += add;
                    if(result.data[i][k] >= MOD) result.data[i][k] -= MOD;
                }
            }
        }
        return result;
    }

    public static Matrix identityMatrix(int size) {
        Matrix result = new Matrix(size, size);
        for (int i = 0; i < size; i++)
            result.data[i][i] = 1;
        return result;
    }

    public Matrix power(long exponent) {
        if (exponent == 0)
            return identityMatrix(rowCount);
        if (exponent == 1)
            return this;
        Matrix result = power(exponent >> 1);
        result = multiply(result, result);
        if ((exponent & 1) == 1)
            result = multiply(result, this);
        return result;
    }

    public Matrix power(long exponent, int MOD) {
        if (exponent == 0)
            return identityMatrix(rowCount);
        if (exponent == 1)
            return this;
        Matrix result = power(exponent >> 1, MOD);
        result = multiply(result, result, MOD);
        if ((exponent & 1) == 1)
            result = multiply(result, this, MOD);
        return result;
    }

    @Override
    public String toString() {
        String ret = "";
        for (long[] row : this.data) {
            ret += Arrays.toString(row) + "\n  ";
        }
        return "{ " + ret + " }";
    }
}
