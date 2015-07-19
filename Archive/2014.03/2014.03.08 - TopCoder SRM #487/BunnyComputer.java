package Tasks;

import javaUtils.CollectionUtils;

import java.util.ArrayList;

public class BunnyComputer {

    int[] a;

    int getBest(ArrayList<Integer> list) {
        a = CollectionUtils.toArray(list);
        int n = a.length;
        if (n == 0) return 0;
        int[] dp = new int[n];
        dp[0] = a[0];
        if (n == 1) return dp[0];
        dp[1] = Math.max(a[0], a[1]);

        for (int i = 2; i < n; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + a[i]);
        }
        return dp[n - 1];
    }

    public int getMaximum(int[] preference, int k) {
        int n = preference.length;
        int ans = 0;
        for (int i = 0; i < k + 1; i++) {
            ArrayList<Integer> li = new ArrayList<Integer>();
            for (int j = i; j + k + 1 < n; j += k + 1) {
                li.add(preference[j] + preference[j + k + 1]);
            }
            ans += getBest(li);
        }
        return ans;
    }
}
