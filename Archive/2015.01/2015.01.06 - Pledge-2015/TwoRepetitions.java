package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;
import javaUtils.SuffixArray;

public class TwoRepetitions {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        String s = in.readString();
        int n = s.length();
        int[] sa = SuffixArray.suffixArray(s);
        int[] lcp = SuffixArray.lcp(sa, s);
        int last = 0;
        long ans = 0;
        long dis = n * (n + 1L) / 2;
        for (int l : lcp) {
            int add = l - last;
            if(add > 0) ans += add;
            last = l;
            dis -= l;
        }
        out.printLine(dis - ans);
    }
}
