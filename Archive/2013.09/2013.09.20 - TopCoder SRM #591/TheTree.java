package Tasks;

public class TheTree {
    public int maximumDiameter(int[] cnt) {
        int res = 0;
        int n = cnt.length;
        int last = n;
        for (int i = n - 1; i >= -1; i--) {
            res = Math.max(n - i - 1 + last - i - 1, res);
            if (i >= 0 && cnt[i] == 1) last = i;
        }
        return res;
    }
}
