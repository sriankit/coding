package Tasks;

import java.util.Arrays;

public class MiniatureDachshund {
    public int maxMikan(int[] mikan, int weight) {
        Arrays.sort(mikan);
        int ret = 0;
        int i = 0;
        while (i < mikan.length && weight + mikan[i] <= 5000) {
            ret++;
            weight += mikan[i];
            i++;
        }
        return ret;
    }
}
