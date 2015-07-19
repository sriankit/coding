package Tasks;

public class IncrementAndDoubling {
    public int getMin(int[] desiredArray) {
        int ans = 0;
        int max2 = 0;
        for (int num : desiredArray) {
            ans += Integer.bitCount(num);
            max2 = Math.max(max2, Integer.toBinaryString(num).length() - 1);
        }
        return ans + max2;
    }
}
