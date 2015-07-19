package Tasks;

import javaUtils.ArrayUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

public class DeliciousDishes {
    int preDP[][];
    int[] arr;
    int[] shift;

    int go(int i, int mask) {
        if (i == -1) {
            if (mask == 1 || mask == 0) return 0;
            return 1;
        }
        if (preDP[i][mask] != -1) return preDP[i][mask];
        int ret = 0;
        for (int j = 0; j < 10; j++) {
            if (j == 0 && mask == 0) ret += go(i - 1, 0);
            else if ((mask & shift[j]) == 0) ret += go(i - 1, mask | shift[j]);
        }
        return preDP[i][mask] = ret;
    }

    int compute(long l) {
        if (l < 0) return 0;
        if (l == 0) return 0;
        int digcnt = 0;
        arr = new int[20];
        long res = l;
        while (res > 0) {
            arr[digcnt++] = (int) (res % 10);
            res /= 10;
        }

        if (digcnt > 10) return go(10, 0);
        int mask = 0;
        int ret = 0;
        boolean ok = true;
        for (int i = digcnt - 1; i >= 0; i--) {
            for (int j = 0; j < arr[i]; j++) {
                if (mask == 0 && j == 0) ret += go(i - 1, 0);
                else if ((mask & shift[j]) == 0) ret += go(i - 1, mask | shift[j]);
            }
            if ((mask & shift[arr[i]]) == 0) mask |= shift[arr[i]];
            else {
                ok = false;
                break;
            }
        }
        if (ok) ret++;
        System.out.println("returning " + ret + " for " + l);
        return ret;
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        shift = new int[12];
        shift[0] = 1;
        for (int i = 1; i < 12; i++) {
            shift[i] = shift[i - 1] << 1;
        }
        preDP = new int[20][4096];
        ArrayUtils.fill2d(preDP, -1);
        //System.out.println(go(10, 0));
        testNumber = in.readInt();
        while (testNumber-- > 0) {
            long l = in.readLong();
            long r = in.readLong();
            out.printLine(compute(r) - compute(l - 1));
        }
    }
}
