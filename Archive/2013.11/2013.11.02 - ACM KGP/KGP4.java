package Tasks;

import javaUtils.AhoCorasickDataStructure;
import javaUtils.InReader;
import javaUtils.OutputWriter;

public class KGP4 {
    String pre;
    int[] last;

    AhoCorasickDataStructure ds;


    int countOcc(int offset) {
        int ret = 0;
        int state = 0;
        for (int i = 0; i < offset; i++) {
            state = ds.trans[state][pre.charAt(i) - '0'];
            if (ds.found[state]) ret++;
        }
        return ret;
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        StringBuilder builder = new StringBuilder();
        int lim = 1000000;
        last = new int[lim + 1];
        for (int i = 1; i <= lim; i++) {
            builder.append(Integer.toString(i));
            last[i] = builder.length();
        }
        pre = builder.toString();
        int t = in.readInt();
        while (t-- > 0) {
            int n = in.readInt();
            int k = in.readInt();
            String s = Integer.toString(k);
            ds = new AhoCorasickDataStructure(10, '0');
            ds.add(s, 0);
            ds.bfs();
            ds.makeTrans();
            out.printLine(countOcc(last[n]));
        }
    }
}
