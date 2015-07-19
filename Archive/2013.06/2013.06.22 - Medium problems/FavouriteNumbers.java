package Tasks;

import javaUtils.AhoCorasickDataStructure;
import javaUtils.ArrayUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

public class FavouriteNumbers {
    int[][] trans;
    AhoCorasickDataStructure ds;
    long[][] preDP;
    long[] powTen;

    long go(int i, int state) {
        if (i == -1) {
            if (ds.found[state]) return 1;
            else return 0;
        }
        if (preDP[i][state] != -1) return preDP[i][state];
        if (ds.found[state]) return preDP[i][state] = powTen[i + 1];
        long ret = 0;
        for (int j = 0; j < 10; j++) {
            ret += go(i - 1, trans[state][j]);
        }
        return preDP[i][state] = ret;
    }

    long computeL(long num) {
        if (num <= 0) return 0;
        long res = num;
        int[] arr = new int[19];
        int i = 0;
        while (res > 0) {
            arr[i++] = (int) (res % 10);
            res /= 10;
        }
        int first = i - 1;
        int q = 0;
        long ret = 0;
        boolean take = ds.found[q];
        long pre = 0;
        int j;
        for (j = first; j >= 0; j--) {
            if (take) {
                ret += num - pre * powTen[j + 1] + 1;
                break;
            }
            for (int dig = 0; dig < arr[j]; dig++) {
                ret += go(j - 1, trans[q][dig]);
            }
            q = trans[q][arr[j]];
            take = ds.found[q];
            pre = (pre << 3) + (pre << 1) + arr[j];
        }
        if (j >= 0) return ret;
        else return take ? ret + 1 : ret;
    }

    long computeLR(long l, long r) {
        return computeL(r) - computeL(l - 1);
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        long l = in.readLong();
        long r = in.readLong();
        long k = in.readLong();
        int n = in.readInt();

        powTen = new long[20];
        powTen[0] = 1;
        for (int i = 1; i < 20; i++) {
            powTen[i] = powTen[i - 1] * 10;
        }
        String[] arr = new String[n];
        ds = new AhoCorasickDataStructure(10, '0');
        for (int i = 0; i < n; i++) {
            arr[i] = in.readString();
            ds.add(arr[i]);
        }
        ds.bfs();
        ds.makeTrans();
        trans = ds.trans;
        //System.out.println("ds done");
        preDP = new long[20][ds.stateCount];
        ArrayUtils.fill2d(preDP, -1);

        if (computeLR(l, r) < k) out.printLine("no such number");
        else {
            long lo = l;
            long hi = r;
            long ans = hi;
            while (lo < hi) {
                long mid = lo + hi >> 1;
                long cnt = computeLR(lo, mid);
                /*System.out.println("lo = " + lo);
                System.out.println("hi = " + hi);
                System.out.println("k = " + k);
                System.out.println("cnt = " + cnt);*/
                if (cnt >= k) {
                    ans = mid;
                    hi = mid;
                } else if (cnt < k) {
                    lo = mid + 1;
                    k -= cnt;
                }
            }
            out.printLine(ans);
            //System.out.println(Arrays.deepToString(trans));
            //System.out.println(Arrays.toString(ds.found));
        }
    }
}
