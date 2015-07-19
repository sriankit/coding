package Tasks;

import javaUtils.ArrayUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Egalitarianism3 {
    int[][] dist;
    public int findSize(ArrayList<Integer> prob, int dt) {
        Collections.shuffle(prob);
        if(prob.size() == 1) return 1;
        if(prob.size() == 0) return 0;
        int comp = 0;
        int start = prob.get(0);
        {
            ArrayList<Integer> go = new ArrayList<Integer>();
            for(int ele : prob) {
                if(dist[start][ele] == dt && start != ele) go.add(ele);
            }
            comp = Math.max(comp, 1 + findSize(go, dt));
        }
        return comp;
    }
    public int maxCities(int n, int[] a, int[] b, int[] len) {
        if(n == 1) return 1;
        dist = new int[n][n];
        ArrayUtils.fill2d(dist, 5001);

        for (int i = 0; i < a.length; i++) {
            dist[a[i]-1][b[i]-1] = len[i];
            dist[b[i]-1][a[i]-1] = len[i];
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }
        int ans = 0;
        for (int start = 0; start < n; start++) {
            int d[] = dist[start];
            HashMap<Integer, ArrayList<Integer> > map = new HashMap<>();
            for (int i = 0; i < n; i++) {
                if(i != start) {
                    int dt = d[i];
                    if(dt == 5001) continue;
                    if(!map.containsKey(dt)) map.put(dt, new ArrayList<Integer>());
                    map.get(dt).add(i);
                }
            }
            int comp = ans;
            for(int dt : map.keySet()) {
                ArrayList<Integer> prob = map.get(dt);
                if(prob.size() + 1 <= comp) continue;
                comp = Math.max(comp, 1 + findSize(prob, dt));
            }
            ans = Math.max(ans, comp);
        }
        return ans;
    }
}
