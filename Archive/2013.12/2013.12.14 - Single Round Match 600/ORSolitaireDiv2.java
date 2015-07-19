package Tasks;

import java.util.Arrays;

public class ORSolitaireDiv2 {
    public int getMinimum(int[] numbers, int goal) {

        int n = numbers.length;
        int ans = n;
        for (int mask = 1; mask < (1 << n); mask++) {
            int rival = n - Integer.bitCount(mask);
            int taken = Integer.bitCount(mask);
            int[] nums = new int[taken];
            int ind = 0;

            for (int j = 0; j < n; j++) {
                if ((mask >> j) % 2 == 1) nums[ind++] = numbers[j];
            }
            int flag[] = new int[taken];
            Arrays.fill(flag, 1);

            for (int i = 0; i < taken; i++) {
                long tmp = goal ^ nums[i];
                if ((tmp | goal) != goal) flag[i] = 0;
            }

            long res = 0;
            for (int i = 0; i < taken; i++) {
                if (flag[i] == 1) res |= nums[i];
            }

            if (res != goal) ans = Math.min(ans, rival);

        }

        return ans;
    }
}
