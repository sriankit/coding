package Tasks;

import java.util.ArrayList;
import java.util.Arrays;

public class Family {
    int n;
    ArrayList<Integer> adj[];
    int col[];
    boolean ok = true;

    public void dfs(int v, int clr) {
        col[v] = clr;
        for (int child : adj[v]) {
            if (col[child] == -1) dfs(child, 1 - clr);
            else if (col[child] == clr)
                ok = false;
        }
    }

    public String isFamily(int[] parent1, int[] parent2) {
        n = parent1.length;
        adj = new ArrayList[n];
        col = new int[n];
        Arrays.fill(col, -1);
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < parent1.length; i++) {
            if (parent1[i] != -1) {
                if (parent1[i] >= i || parent2[i] >= i) return "Impossible";
                else {
                    int v1 = parent1[i];
                    int v2 = parent2[i];
                    adj[v1].add(v2);
                    adj[v2].add(v1);
                }
            }
        }
        for (int i = 0; i < n; i++) {
            if (col[i] == -1) dfs(i, 0);
        }
        if (ok) return "Possible";
        else return "Impossible";
    }
}
