package Tasks;

public class Unique {
    public String removeDuplicates(String S) {
        String res = "";
        for (int i = 0; i < S.length(); i++) {
            boolean f = true;
            for (int j = 0; j < res.length(); j++) {
                if (S.charAt(i) == res.charAt(j)) f = false;
            }
            if (f) res += S.charAt(i);
        }
        return res;
    }
}
