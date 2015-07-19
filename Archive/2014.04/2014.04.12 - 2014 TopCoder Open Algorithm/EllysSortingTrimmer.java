package Tasks;

import java.util.Arrays;

public class EllysSortingTrimmer {
    char min(String s) {
        char mi = s.charAt(0);
        for (int i = 0; i < s.length(); i++) {
            if (mi > s.charAt(i)) mi = s.charAt(i);
        }
        return mi;
    }

    String sortString(String s) {
        char[] sc = s.toCharArray();
        Arrays.sort(sc);
        StringBuilder builder = new StringBuilder();
        for (char c : sc) builder.append(c);
        return builder.toString();
    }

    public String getMin(String S, int L) {
        char[] str = S.toCharArray();
        /*for (int i = 0; i < S.length() - L; i++) {
            if(str[i] == min(S.substring(i, i + L))) continue;
            else {
                Arrays.sort(str, i, i + L);
                String tmp = "";
                for (int j = i; j < i + L; j++) {
                    tmp += str[j];
                }
                return S.substring(0, i + 1) + tmp;
            }
        } *
        Arrays.sort(str);
        String tmp = "";
        for (int i = 0; i < L; i++) {
            tmp += str[i];
        }
        return tmp;
        */
        for (int i = S.length() - L; i >= 0; i--) {
            S = S.substring(0, i) + sortString(S.substring(i, i + L));

        }
        return S;
    }
}
