package Tasks;

public class BallRemoval {
    String s;
    int n;
    boolean dp[][];
    boolean done[][];

    public String canLeave(String label) {
        s = label;
        n = s.length();
        dp = new boolean[n][n];
        done = new boolean[n][n];
        String ans = "";
        for (int i = 0; i < n; i++) {
            if (collapse(0, i - 1) && collapse(i + 1, n - 1)) ans += 'o';
            else ans += '.';
        }
        return ans;
    }

    private boolean collapse(int i, int j) {
        if (i > j) return true;
        if (i == j) return false;
        if (j - i == 1) {
            if (i == 0) return s.charAt(j) == '<';
            if (j == n - 1) return s.charAt(i) == '>';
            return s.charAt(i) == '>' || s.charAt(j) == '<';
        }
        if (done[i][j]) return dp[i][j];
        done[i][j] = true;
        if (collapse(i + 1, j - 1)) {
            if ((i > 0 && s.charAt(i) == '>') || (j < n - 1 && s.charAt(j) == '<')) return dp[i][j] = true;
        }
        for (int k = i + 1; k < j - 1; k++) {
            if (collapse(i, k) && collapse(k + 1, j))
                return dp[i][j] = true;
        }
        return dp[i][j] = false;
    }
}

