package Tasks;

import java.util.Arrays;

public class ShoppingSurveyDiv1 {
    int calc(int n, int m, int[] a, int K) {
        int[] via = new int[n];
        int[] b = new int[n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(via, 0);
            for (int k = 0; k < a[i]; k++) {
                int mbi = -1;
                for (int j = 0; j < n; j++) {
                    if (via[j] == 0 && (mbi == -1 || b[mbi] > b[j])) {
                        mbi = j;
                    }
                }
                via[mbi] = 1;
                b[mbi]++;
            }
        }
        int ana = 0;
        for (int i = 0; i < n; i++) {
            if(b[i] >= K) return -1;
        }
        return ana;
    }
    boolean ok(int[] a, int bigNum, int n, int K) {
        for (int i = 0; i < a.length; i++) {
            a[i] = Math.max(a[i] - bigNum, 0);
        }
        int res = calc(n - bigNum, a.length, a, K);
        return res != -1;
    }
    public int minValue(int N, int K, int[] s) {
        int lo = 0, hi = N;
        int ans = 0;
        while(lo <= hi) {
            int mid = lo + hi >> 1;
            if(ok(s.clone(), mid, N, K)) {
                ans = mid;
                hi = mid - 1;
            } else lo = mid + 1;
        }
        return ans;
    }
}
