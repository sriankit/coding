package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;
import javaUtils.StringProcess;

public class Lapindromes {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        testNumber = in.readInt();
        while (testNumber-- > 0) {
            String s = in.readString();
            int len = s.length();
            StringProcess pre = new StringProcess(s.substring(0, len / 2));
            StringProcess suf = new StringProcess(s.substring(len - len / 2));
            if (pre.getSorted().equals(suf.getSorted())) out.printLine("YES");
            else out.printLine("NO");
        }
    }
}
