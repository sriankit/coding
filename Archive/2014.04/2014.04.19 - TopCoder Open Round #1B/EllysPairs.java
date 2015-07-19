package Tasks;

import java.util.Arrays;

public class EllysPairs {
    public int getDifference(int[] knowledge) {
        Arrays.sort(knowledge);
        int n = knowledge.length;
        int[] op = new int[n >> 1];
        for (int i = 0; i < n >> 1; i++) {
            op[i] = knowledge[i] + knowledge[n - i - 1];
        }
        Arrays.sort(op);
        return op[n / 2 - 1] - op[0];
    }
}
