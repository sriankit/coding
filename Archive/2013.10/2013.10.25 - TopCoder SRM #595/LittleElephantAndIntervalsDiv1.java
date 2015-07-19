package Tasks;

import java.util.TreeSet;

public class LittleElephantAndIntervalsDiv1 {
    public long getNumber(int M, int[] L, int[] R) {
        int[] col = new int[M + 1];
        int n = L.length;
        int c = 1;
        for (int i = 0; i < n; i++) {
            for (int j = L[i]; j <= R[i]; j++) {
                col[j] = c;
            }
            c++;
        }
        TreeSet<Integer> set = new TreeSet<Integer>();
        for (int i = 1; i <= M; i++) {
            set.add(col[i]);
        }
        long ans = 0;
        for (int cl : set) {
            if (cl > 0) ans++;
        }
        return 1L << ans;
    }
}
