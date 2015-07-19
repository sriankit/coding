package javaUtils;

public class MaxBIT {

    public long[] tree;
    int maxVal;

    public MaxBIT(int maxVal) {
        this.maxVal = maxVal;
        tree = new long[maxVal + 1];
    }

    public int getMax(int idx) {
        long ret = 0;
        int ind = -1;
        while (idx > 0) {
            if (tree[idx] > ret) {
                ret = tree[idx];
                ind = idx;
            }
            idx -= (idx & -idx);
        }
        return ind;
    }

    public void update(int idx, long val) {
        while (idx < maxVal) {
            tree[idx] = Math.max(tree[idx], val);
            idx += (idx & -idx);
        }
    }
}
