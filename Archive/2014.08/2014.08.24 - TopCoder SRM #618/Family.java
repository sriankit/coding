package Tasks;

import javaUtils.IntegerUtils;

import javax.swing.text.AsyncBoxView;
import java.beans.Visibility;
import java.util.ArrayList;
import java.util.List;

public class Family {
    int vis[];
    ArrayList<Integer> [] list;
    int dfs(int v, int d) {
        if(vis[v] == 0) {
            vis[v] = d;
            for(int child : list[v]) {
                if( dfs(child, d + 1) == 0) return 0;
            }
        } else return (vis[v] % 2 == d % 2) ? 1 : 0;
        return 1;
    }

    public String isFamily(int[] parent1, int[] parent2) {
        int n = parent1.length;
        vis = new int[n];
        String[] pos = new String []{"Possible", "Impossible"};
         list = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            list[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < n; i++) {
            if(parent1[i] != -1) {
                int p1 = parent1[i];
                int p2 = parent2[i];
                list[p1].add(p2);
                list[p2].add(p1);
            }
        }
        for (int i = 0; i < n; i++) {
            if(parent1[i] != -1) {
                if(vis[parent1[i]] == 0 && dfs(parent1[i], 1) == 0) return pos[1];
            }
        }
        return pos[0];
    }
}
