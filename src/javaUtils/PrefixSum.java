package javaUtils;

public class PrefixSum {
    long[] pre;

    public PrefixSum(long[] arr) {
        pre = new long[arr.length];
        pre[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            pre[i] = pre[i - 1] + arr[i];
        }
    }

    public PrefixSum(int[] arr) {
        pre = new long[arr.length];
        pre[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            pre[i] = pre[i - 1] + arr[i];
        }
    }

    public long getPrefixSum(int i) {
        if (i < 0) return 0;
        else if (i >= pre.length) throw new IllegalArgumentException();
        else return pre[i];
    }

    public long getSum(int i, int j) {
        return getPrefixSum(j) - getPrefixSum(i - 1);
    }
}
