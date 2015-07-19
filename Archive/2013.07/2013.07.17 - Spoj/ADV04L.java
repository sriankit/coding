package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.Arrays;

public class ADV04L {
    //TLE in java AC in cpp
    public void solve(int testNumber, InReader in, OutputWriter out) {
        long[] fibs = new long[77];
        long lim = (long) 1e15;
        fibs[0] = 0;
        fibs[1] = 1;
        for (int i = 2; i < 77; i++) {
            fibs[i] = fibs[i - 1] + fibs[i - 2];
        }

        testNumber = in.readInt();
        while (testNumber-- > 0) {
            long ans = 0;
            long m = in.readLong();
            int ind;
            do {
                assert (m > 0);
                ind = Arrays.binarySearch(fibs, m);
                //System.out.println("got " + ind);
                int x;
                if (ind < 0) x = -(ind + 1);
                else x = ind + 1;
                ans += fibs[x];
                //System.err.println(fibs[x - 1]);
                m -= fibs[x - 1];
                if (fibs[x - 1] == 1) {
                    ans -= fibs[x];
                    ans += 2;
                }
            } while (ind < 0);
            out.printLine(ans);
            //System.out.println("done " + testNumber);
        }
    }
}
