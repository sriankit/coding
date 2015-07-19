package Tasks;

public class IncrementAndDoubling {
    public int getMin(int[] desiredArray) {
        int ans = 0;
        int m2 = 0;
        for (int num : desiredArray) {
            ans += Integer.bitCount(num);
            m2 = Math.max(m2, Integer.toBinaryString(num).length() - 1);
        }
        return ans + m2;
    }
}
