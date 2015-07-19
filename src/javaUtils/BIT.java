package javaUtils;

public class BIT {
    int maxVal;
    long[] BIT;
    boolean mod;
    long MOD;

    public BIT(int n) {
        this.BIT = new long[n + 1];
        maxVal = n;
        mod = false;
    }

    public BIT(int n, long MOD) {
        this.BIT = new long[n + 1];
        maxVal = n;
        this.MOD = MOD;
        mod = true;
    }

    public void update(int idx, long val) {
        while (idx <= maxVal) {
            //BIT[idx] = Math.max(BIT[idx], val);
            BIT[idx] += val;
            //if(mod && BIT[idx] >= MOD) BIT[idx] %= MOD;
            idx += (idx & -idx);
        }
    }

    public long freqTo(int idx) {
        long sm = 0;
        while (idx > 0) {
            //sm = Math.max(sm, BIT[idx]);
            sm += BIT[idx];
            //if(mod && sm >= MOD) sm %= MOD;
            idx -= (idx & -idx);
        }
        return sm;
    }

    public long rangeFreq(int l, int r) {
        return freqTo(r) - freqTo(l - 1);
    }

    public long freqAt(int idx) {
        long sm = BIT[idx];
        int y;
        if (idx > 0) {
            int z = idx - (idx & -idx);
            y = idx - 1;
            while (y != z) {
                sm -= BIT[y];
                y -= (y & -y);
            }
        }
        return sm;
    }
}
