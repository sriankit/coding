package Tasks;

import javaUtils.ArrayUtils;

public class ManySquares {
    public int howManySquares(int[] sticks) {
        int ans = 0;
        for (int i = 0; i <= 1000; i++) {
            ans += ArrayUtils.count(sticks, i) / 4;
        }
        return ans;
    }
}
