package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

public class TaskA {
    int countLuckyDigits(String st) {
        int count = 0;
        for (int i = 0; i < st.length(); i++) {
            if (st.charAt(i) == '4' || st.charAt(i) == '7') count++;
        }
        return count;
    }

    boolean isLucky(String st) {
        int cntLucky = countLuckyDigits(st);
        if (cntLucky == st.length()) return true;
        else return false;
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        String input = in.readString();
        String luckyCount = String.valueOf(countLuckyDigits(input));
        if (isLucky(luckyCount)) out.printLine("YES");
        else out.printLine("NO");
    }
}
