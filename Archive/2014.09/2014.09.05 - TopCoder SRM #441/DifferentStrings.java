package Tasks;

public class DifferentStrings {
    public int diff(String s1, String s2) {
        System.out.println(s1 + " " + s2);
        int c = 0;
        for (int i = 0; i < s1.length(); i++) {
            if(s1.charAt(i) != s2.charAt(i)) c ++;
        }
        return c;
    }
    public int minimize(String A, String B) {
        int n1 = A.length();
        int n2 = B.length();
        int ans = n2;
        for (int i = 0; i + n1 <= n2; i++) {
            ans = Math.min(ans, diff(A, B.substring(i, i + n1)));
        }
        return ans;
    }
}
