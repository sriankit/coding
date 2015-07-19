package Tasks;

public class PotentialArithmeticSequence {
    public int find_ans(int[] d, final int idx) {
        if (d[idx] == 0) return 1;
        int ret = 1;
        int st = (d[idx] >= 7 ? (1 << 7) : (1 << d[idx]));
        for (int j = idx + 1; j < d.length; j++) {
            st++;
            if (Integer.numberOfTrailingZeros(st) == d[j]) ret++;
            else if ((st & (st - 1)) == 0 && (d[j] >= Integer.toBinaryString(st).length())) {
                st = (d[j] >= 7 ? (1 << 7) : (1 << d[j]));
                ret++;
            } else break;
        }
        return ret;
    }

    public int numberOfSubsequences(int[] d) {
        int n = d.length;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans += find_ans(d, i);
            if (d[i] == 0 && i < n - 1 && d[i + 1] > 0) {
                ans += find_ans(d, i + 1);
            }
        }
        return ans;
    }
}
