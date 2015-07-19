package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

public class NUMOFPAL {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        String s = in.readString();
        char[] str = s.toCharArray();
        int cnt = 0;
        int n = s.length();
        for (int pivot = 0; pivot < s.length(); pivot++) {
            for (int i = 0; pivot + i < n && pivot - i >= 0; i++) {
                if (str[pivot + i] == str[pivot - i]) cnt++;
                else break;
            }
            for (int i = 0; pivot + i < n && pivot - i - 1 >= 0; i++) {
                if (str[pivot + i] == str[pivot - i - 1]) cnt++;
                else break;
            }
        }
        out.printLine(cnt);
    }
}
