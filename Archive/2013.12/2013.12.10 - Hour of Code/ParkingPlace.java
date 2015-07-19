package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.HashSet;

public class ParkingPlace {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        long n = in.readLong();
        int m = in.readInt();
        int k = in.readInt();
        int MOD1 = 1000007;
        long MOD2 = 10000000007L;
        HashSet<Long> hash[] = new HashSet[MOD1];
        for (int i = 0; i < MOD1; i++) {
            hash[i] = new HashSet<Long>();
        }
        for (int i = 0; i < m; i++) {
            long v = in.readLong();
            hash[(int) (v % MOD1)].add(v % MOD2);
        }
        for (int i = 0; i < k; i++) {
            long v = in.readLong();
            int ind = (int) (v % MOD1);
            if (hash[ind].contains(v % MOD2)) out.printLine(1);
            else out.printLine(0);
        }
    }

}
