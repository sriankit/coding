package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

public class TaskC {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int num = in.readInt();
        int n4 = 0, n7 = 0, bufn7 = 0;
        for (int i = num; i >= 0; ) {
            if (i % 4 == 0) {
                n4 = i / 4;
                n7 = bufn7;
            }
            i -= 7;
            bufn7++;
        }
        if (n4 + n7 == 0) out.printLine(-1);
        else {
            while (n4-- > 0) out.print('4');
            while (n7-- > 0) out.print('7');
        }
    }
}
