package Tasks;

public class TaroString {
    public String getAnswer(String S) {
        int cc = 0;
        for (int i = 0; i < S.length(); i++) {
            if (S.charAt(i) == 'C') cc++;
        }
        int ca = 0;
        for (int i = 0; i < S.length(); i++) {
            if (S.charAt(i) == 'A') ca++;
        }
        int ct = 0;
        for (int i = 0; i < S.length(); i++) {
            if (S.charAt(i) == 'T') ct++;
        }
        if (cc == 1 && ca == 1 && ct == 1 && S.indexOf('C') < S.indexOf('A') && S.indexOf('A') < S.indexOf('T'))
            return "Possible";
        else return "Impossible";
    }
}
