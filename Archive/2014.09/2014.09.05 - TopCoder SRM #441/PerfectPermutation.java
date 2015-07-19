package Tasks;

public class PerfectPermutation {
    public int reorder(int[] P) {
        int n = P.length;
        boolean vis[] = new boolean[n];
        int cy = 0;
        for (int i = 0; i < n; i++) {
            if(!vis[i]) {
                int j = i;
                while(!vis[j]) {
                    vis[j] = true;
                    j = P[j];
                }
                cy ++;
            }
        }
        return cy == 1 ? 0 : cy;
    }
}
