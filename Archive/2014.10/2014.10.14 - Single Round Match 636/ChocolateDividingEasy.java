package Tasks;


public class ChocolateDividingEasy {
    int n, m;
    int cost[][];
    int go(int r1, int r2, int c1, int c2) {
        int c[][] = new int[3][3];
        c[0][0] = cost[r1][c1];
        c[0][1] = cost[r1][c2] - cost[r1][c1];
        c[0][2] = cost[r1][m] - cost[r1][c2];
        c[1][0] = cost[r2][c1] - cost[r1][c1];
        c[1][1] = cost[r2][c2] - cost[r1][c2] - cost[r2][c1] + cost[r1][c1];
        c[1][2] = cost[r2][m] - cost[r2][c2] - cost[r1][m] + cost[r1][c2];
        c[2][0] = cost[n][c1] - cost[r2][c1];
        c[2][1] = cost[n][c2] - cost[n][c1] - cost[r2][c2] + cost[r2][c1];
        c[2][2] = cost[n][m] - cost[n][c2] - cost[r2][m] + cost[r2][c2];
        int mn = c[0][0];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mn = Math.min(c[i][j], mn);
            }
        }
        /*System.out.println(r1 + " " + r2 + " " + c1 + " " + c2) ;
        for (int i = 0; i < 3; i++) {
            System.out.println(Arrays.toString(c[i]));
        }*/
        return mn;
    }
    public int findBest(String[] chocolate) {
        n = chocolate.length;
        m = chocolate[0].length();
        cost = new int[n + 1][m + 1];
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < m + 1; j++) {
                int dig = chocolate[i - 1].charAt(j - 1) - '0';
                cost[i][j] = cost[i - 1][j] + cost[i][j - 1] + dig - cost[i - 1][j - 1];
            }
        }
        /*for (int i = 0; i < n + 1; i++) {
            System.out.println(Arrays.toString(cost[i]));
        }*/
        int res = 0;
        for (int i = 1; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = 1; k < m; k++) {
                    for (int l = k + 1; l < m; l++) {
                        int comp = go(i, j, k, l);
                        if(comp > res) {
                            res = comp;
                            System.out.println(i + " " + j + " " + k + " " + l);
                        }
                    }
                }
            }
        }
        //System.out.println(go(1, 2, 1, 2));
        return res;
    }
}
