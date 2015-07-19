package Tasks;

import javaUtils.ArrayUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.Arrays;

public class LuckyNumbers {
    long[][][] preDp;
    int[] primes = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163};
    int[] indices;

    long go(int i, int sumdigs, int sumsq) {
        if (sumdigs < 0 || sumsq < 0) return 0;
        if (i == 0) {
            if (sumdigs < 10 && sumsq == sumdigs * sumdigs) return 1;
            else return 0;
        }
        if (preDp[i][sumdigs][sumsq] != -1) return preDp[i][sumdigs][sumsq];
        long ret = 0;
        for (int j = 0; j < 10; j++) {
            ret += go(i - 1, sumdigs - j, sumsq - j * j);
        }
        return preDp[i][sumdigs][sumsq] = ret;
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        preDp = new long[19][165][165];
        ArrayUtils.fill3d(preDp, -1);
        testNumber = in.readInt();
        indices = new int[170];
        //indices stores index to start from given sum s<= primes[ind]
        int ind = 0;
        for (int s = 0; s < 163; s++) {
            if (s <= primes[ind]) indices[s] = ind;
            else indices[s] = ind++;
        }
        while (testNumber-- > 0) {
            long l = in.readLong();
            long r = in.readLong();
            //System.out.println("testing " + go(1, 2, 2));
            out.printLine(compute(r) - compute(l - 1));
        }
    }

    private int getIndex(int s) {
        if (s >= 162) return 36;
        else return indices[s];
    }

    private long compute(long num) {
        if (num < 10) return 0;
        int[] digs = new int[19];
        long res = num;
        int j = 0;
        while (res > 0) {
            digs[j++] = (int) (res % 10);
            res /= 10;
        }
        int first = j - 1;
        long ret = 0;
        int takenSum = 0, takenSq = 0;
        for (int i = first; i >= 0; i--) {
            {
                for (int k = 0; k < digs[i]; k++) {
                    int sum = takenSum + k;
                    int sumSq = takenSq + k * k;
                    if (i > 0)
                        for (int l = getIndex(sum); primes[l] <= j * 9; l++)
                            for (int m = getIndex(sumSq); m < primes.length && primes[m] <= j * 81; m++)
                                ret += go(i - 1, primes[l] - sum, primes[m] - sumSq);
                    else if (Arrays.binarySearch(primes, sum) >= 0 && Arrays.binarySearch(primes, sumSq) >= 0) ret++;
                    //System.out.println("i = " + i + " with k = " + k + " adding " + ret);
                }
            }
            takenSum += digs[i];
            takenSq += digs[i] * digs[i];
        }
        if (Arrays.binarySearch(primes, takenSum) >= 0 && Arrays.binarySearch(primes, takenSq) >= 0) ret++;
        //System.out.println("takenSum = " + takenSum);
        //System.out.println("takenSq = " + takenSq);
        //System.out.println("returning " + ret + " " + " for " + num);
        return ret;
    }
}
