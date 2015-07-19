package Tasks;

public class BuildingRoutes {
    public int build(String[] dist, int T) {
        int n = dist.length;
        int d[][] = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                d[i][j] = dist[i].charAt(j) - '0';
            }
        }
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    d[i][j] = Math.min(d[i][j], d[i][k] + d[k][j]);
                }
            }
        }
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int cnt = 0;
                if(i != j) {
                    for (int a = 0; a < n; a++) {
                        for (int b = 0; b < n; b++) {
                            if(a != b && d[a][i] + (dist[i].charAt(j) - '0') + d[j][b] == d[a][b]) cnt ++;
                        }
                    }
                }
                if(cnt >= T) ans += dist[i].charAt(j) - '0';
            }
        }
        return ans;
    }
}
