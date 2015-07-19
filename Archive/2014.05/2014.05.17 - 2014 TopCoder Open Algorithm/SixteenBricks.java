package Tasks;

import java.util.Arrays;

public class SixteenBricks {
    public int maximumSurface(int[] height) {
        int[] a = new int[]{4, 4, 3, 3, 3, 3, 2, 2};
        Arrays.sort(height);
        int ans = 0;
        for (int i = 0; i < 16; i++) {
            ans += 4 * height[i] + 1;
        }
        for (int i = 0; i < 8; i++) {
            ans -= 2 * a[i] * height[i];
        }
        int ab = 16;
        for (int i = 0; i < 2; i++) {
            ab -= 4 * height[i];
        }
        for (int i = 2; i < 6; i++) {
            ab -= 2 * height[i];
        }
        for (int i = 8; i < 16; i++) {
            ab += 4 * height[i];
        }
        System.out.println(ab);
        return ans;
    }
}
