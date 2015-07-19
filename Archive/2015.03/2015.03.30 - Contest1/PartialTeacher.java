package Tasks;

import javaUtils.ArrayUtils;
import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.ArrayList;
import java.util.List;

public class PartialTeacher {

    int[] marks;
    int k;

    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        long[] fav = IOUtils.readLongArray(in, n);
        int k = in.readInt();
        marks = IOUtils.readIntArray(in, k);
        k = ArrayUtils.maxElement(marks);
        BitmaskMatrix matrix = new BitmaskMatrix(k);
        boolean dp[] = new boolean[k];
        for (int mark : marks) {
            dp[mark] = true;
        }
        for (int i = 0; i < k; i++) {
            if(! dp[i]) {
                for (int j = 0; j < i; j++) {
                    dp[i] |= dp[j] && dp[i - j];
                }
            }
        }
        for (int i = 1; i < k; i++) {
            matrix.set(i, i - 1);
        }
        List<Long> values = new ArrayList<Long>();
        values.add(0L);
        long last = 0L;
        while (values.size() < 52) {
            long lowerBound = 2 * last + 1;
            long next = getNext(lowerBound);
            values.add(next);
            last = next;
        }

        long dp[] = new long[n];


    }

    long getNext(long lowerBound) {

        BitmaskMatrix powerMatrix = matrix.power(lowerBound);
        int add = 0;
        while(!powerMatrix.get(0, 0)) {
            add++;
            powerMatrix = powerMatrix.multiply(matrix);
        }
        return lowerBound + add;
    }

    class BitmaskMatrix {
        int size;
        long[] rows, cols;
        final long ONE = 1;
        public BitmaskMatrix(int n) {
            this.size = n;
            rows = new long[n];
            cols = new long[n];
        }

        public void set(int i, int j) {
            this.rows[i] &= ONE << (size - j - 1);
            this.cols[j] &= ONE << (size - i - 1);
        }

        public boolean get(int i, int j) {
            return (rows[i] & (ONE << j)) > 0;
        }

        public BitmaskMatrix multiply(BitmaskMatrix M) {
            BitmaskMatrix res = new BitmaskMatrix(M.size);
            int n = M.size;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if((this.rows[i] & M.cols[j]) > 0) {
                        res.rows[i] &= (ONE << (n - j - 1));
                        res.cols[j] &= (ONE << (n - i - 1));
                    }
                }
            }
            return res;
        }

        public BitmaskMatrix power(long pw) {
            BitmaskMatrix base = this;
            BitmaskMatrix res = new BitmaskMatrix(this.size);
            while(pw > 0) {
                if((pw & 1) == 1) res = res.multiply(base);
                base = base.multiply(base);
                pw >>= 1;
            }
            return res;
        }

    }
}

