package Tasks;

import java.util.Arrays;

public class CuttingBitString {
    public int getmin(String s) {
        int n = s.length();
        int[] dp = new int[n];
        Arrays.fill(dp, 100);
        if (s.charAt(0) == '1') dp[0] = 1;
        for (int i = 1; i < n; i++) {
            long num = s.charAt(i) - '0';
            long exp = 2;
            for (int j = i - 1; j >= 0; j--) {
                if (dp[j] != 100 && valid(num) && s.charAt(j + 1) == '1') {
                    dp[i] = Math.min(dp[i], dp[j] + 1);
                }
                num += (s.charAt(j) - '0') * exp;
                exp <<= 1;
            }
            if (valid(num) && s.charAt(0) == '1') dp[i] = 1;
        }
        System.out.println(Arrays.toString(dp));
        return dp[n - 1] != 100 ? dp[n - 1] : -1;
    }

    private boolean valid(long num) {
        if (num == 0) return false;
        if (num == 1) return true;
        while (num > 1 && num % 5 == 0) num /= 5;
        if (num > 1) return false;
        else return true;
    }
}
