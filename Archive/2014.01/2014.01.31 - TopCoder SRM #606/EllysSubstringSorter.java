package Tasks;

import java.util.Arrays;
import java.util.TreeSet;

public class EllysSubstringSorter {
    public String getMin(String S, int L) {
        char[] arr = S.toCharArray();
        TreeSet<String> set = new TreeSet<String>();
        for (int i = 0; i <= S.length() - L; i++) {
            StringBuilder builder = new StringBuilder();
            char tmp[] = S.substring(i, i + L).toCharArray();
            Arrays.sort(tmp);
            for (int j = 0; j < S.length(); j++) {
                if (j >= i && j < i + L) builder.append(tmp[j - i]);
                else builder.append(S.charAt(j));
            }
            set.add(builder.toString());
        }
        return set.pollFirst();
    }
}
