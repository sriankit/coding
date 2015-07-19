package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.Arrays;

public class LongestCommonPattern {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        char[] s1 = in.readString().toCharArray();
        char[] s2 = in.readString().toCharArray();
        Arrays.sort(s1);
        Arrays.sort(s2);
        int i = 0, j = 0;
        int ans = 0;
        while (i < s1.length && j < s2.length) {
            if (s1[i] == s2[j]) {
                ans++;
                i++;
                j++;
            } else if (s1[i] < s2[j])
                i++;
            else j++;
        }
        out.printLine(ans);
    }
}
