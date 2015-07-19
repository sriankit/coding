package Tasks;

public class AB {
    int n, k;
    boolean dp[][][];
    boolean done[][][];

    boolean f(int i, int a, int tot) {
        if(i == n && tot == k) return dp[i][a][tot] = true;
        if(i == n) return dp[i][a][tot] = false;
        if(!done[i][a][tot]) {
            done[i][a][tot] = true;
            dp[i][a][tot] = f(i + 1, a, tot + a) || f(i + 1, a + 1, tot);
        }
        return dp[i][a][tot];
    }

    public String createString(int N, int K) {
        n = N;
        k = K;
        dp = new boolean[N + 1][N + 1][N * N];
        done = new boolean[N + 1][N + 1][N * N];
        f(0, 0, 0);
        if(dp[0][0][0]) {
            String s = "";
            int a = 0, tot = 0;
            for (int i = 0; i < N; i++) {
                if(a >= N) System.out.println(a);
                if(done[i][a][tot] && (dp[i][a][tot] == dp[i + 1][a][tot + a])) {
                    tot += a;
                    s += 'B';
                }
                else {
                    a ++;
                    s += 'A';
                }
            }
            return s;
        }
        else return "";
    }
}
