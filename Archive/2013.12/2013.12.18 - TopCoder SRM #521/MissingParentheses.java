package Tasks;

public class MissingParentheses {
    public int countCorrections(String par) {
        int open = 0;
        int close = 0;
        int ans = 0;
        char[] str = par.toCharArray();
        for (char c : str) {
            if (c == '(') open++;
            else close++;
            if (open - close < 0) {
                ans++;
                open++;
            }
        }
        ans += open - close;
        return ans;
    }
}
