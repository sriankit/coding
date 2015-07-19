package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

public class TaskB {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int num = in.readInt();
        char[] ans = new char[]{'a', 'b', 'c', 'd'};
        for (int i = 0; i < num; i++) {
            out.print(ans[i % 4]);
        }

    }
}
