package Tasks;

import javaUtils.ArrayUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.Arrays;

public class LuckyNumbersRevamped {
    long[][][] preDp;
    int[] primes = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661, 673, 677, 683, 691, 701, 709, 719, 727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797, 809, 811, 821, 823, 827, 829, 839, 853, 857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937, 941, 947, 953, 967, 971, 977, 983, 991, 997, 1009, 1013, 1019, 1021, 1031, 1033, 1039, 1049, 1051, 1061, 1063, 1069, 1087, 1091, 1093, 1097, 1103, 1109, 1117, 1123, 1129, 1151, 1153, 1163, 1171, 1181, 1187, 1193, 1201, 1213, 1217, 1223, 1229, 1231, 1237, 1249, 1259, 1277, 1279, 1283, 1289, 1291, 1297, 1301, 1303, 1307, 1319, 1321, 1327, 1361, 1367, 1373, 1381, 1399, 1409, 1423, 1427, 1429, 1433, 1439, 1447, 1451, 1453, 1459, 1471, 1481, 1483, 1487, 1489, 1493, 1499};

    long go(int i, int sumdigs, int sumsq) {
        if (i == -1) {
            if (Arrays.binarySearch(primes, sumdigs) >= 0 && Arrays.binarySearch(primes, sumsq) >= 0) return 1;
            else return 0;
        }
        if (preDp[i][sumdigs][sumsq] != -1) return preDp[i][sumdigs][sumsq];

        long ret = 0;
        for (int j = 0; j < 10; j++) {
            ret += go(i - 1, sumdigs + j, sumsq + j * j);
        }
        return preDp[i][sumdigs][sumsq] = ret;
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        preDp = new long[19][165][1500];
        ArrayUtils.fill3d(preDp, -1);
        testNumber = in.readInt();

        while (testNumber-- > 0) {
            long l = in.readLong();
            long r = in.readLong();
            //System.out.println("testing " + go(17, 0, 0));
            out.printLine(compute(r) - compute(l - 1));
        }
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
            for (int k = 0; k < digs[i]; k++) {
                int sum = takenSum + k;
                int sumSq = takenSq + k * k;
                if (i > 0)
                    ret += go(i - 1, takenSum + k, takenSq + k * k);
                else if (Arrays.binarySearch(primes, sum) >= 0 && Arrays.binarySearch(primes, sumSq) >= 0) ret++;
                //System.out.println("i = " + i + " with k = " + k + " adding " + ret);
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
