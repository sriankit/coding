package javaUtils;

public class MinBIT {
    int maxVal;
    long[] BIT;

    public MinBIT(int n) {
        this.BIT = new long[n + 1];
        maxVal = n;
    }

    public void update(int idx, long val) {
        while (idx <= maxVal) {
            if (BIT[idx] == 0 || BIT[idx] > val)
                BIT[idx] = val;
            idx += (idx & -idx);
        }
    }

    public long freqTo(int idx) {
        long sm = Integer.MAX_VALUE;
        while (idx > 0) {
            if (BIT[idx] > 0) sm = Math.min(BIT[idx], sm);
            idx -= (idx & -idx);
        }
        return sm;
    }
}
