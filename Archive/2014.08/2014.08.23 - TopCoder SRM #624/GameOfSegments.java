package Tasks;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class GameOfSegments {
    public int winner(int N) {
        int [] grundy = new int[N + 1];
        for (int i = 0; i <= N; i++) {
            Set<Integer> vals = new TreeSet<>();
            for (int j = 0; i - j - 2 >= 0; j++) {
                vals.add(grundy[j] ^ grundy[i - j -2]);
            }
            int ptr = 0;
            for (int num = 0; ; num++) {
                if (!vals.contains(num)) {
                    grundy[i] = num;
                    break;
                }
            }
        }
        return grundy[N] == 0 ? 2 : 1;
    }
}
