package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

public class TaskC {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int k = in.readInt();
        if (k > n || (n > 1 && k == 1)) out.printLine(-1);
        else {
            if (n == 1) {
                out.printLine("a");
                return;
            }
            int num = n - k + 2;
            String ans = "";
            StringBuilder build = new StringBuilder();
            for (int i = 0; i < num >> 1; i++) {
                build.append("ab");
            }
            ans = build.toString();
            if (num % 2 == 1) ans += "a";
            build = new StringBuilder();
            for (int i = 0; i < k - 2; i++) {
                build.append((char) ('c' + i));
            }
            ans += build.toString();
            out.printLine(ans);
        }

    }
}
