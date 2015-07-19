package Tasks;

import javaUtils.*;

public class BdayGame {

    long a[];
    int arr[];
    int pre[];

    int getExponent(long number, int prime) {
        int expo = 0;
        while(number % prime == 0) {
            expo++;
            number /= prime;
        }
        return expo;
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int m = in.readInt();
        a = IOUtils.readLongArray(in, n);
        arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = getExponent(a[i] + 1, m);
        }
        pre = new int[n];
        pre[0] = arr[0];
        for (int i = 1; i < n; i++) {
            pre[i] = pre[i - 1] + arr[i];
        }
        int totalS = pre[n - 1];
        //IOUtils.writeIntArray(out, pre, 0);
        long[] poly1 = new long[totalS + 1];
        long[] poly2 = new long[totalS + 1];
        for (int i = 0; i < n; i++) {
            poly1[pre[i]]++;
            poly2[totalS - pre[i]]++;
        }
        poly1[0]++;
        poly2[totalS]++;
        long[] res = FastFourierTransform.multiply(poly1, poly2);

        //System.err.print(totalS);

        boolean[] isPrime = IntegerUtils.generatePrimalityTable(totalS + 1);
        long ways = 0;
        for (int i = totalS + 1; i < 2 * totalS + 1; i++) {
            int actualSum = i - totalS;
            //System.err.println(actualSum + " " + res[actualSum]);
            if(isPrime[actualSum]) ways += res[i] * actualSum;
        }
        double totalWays = (long)n * (n - 1) / 2;
        out.printLine(ways / totalWays);
    }
}
