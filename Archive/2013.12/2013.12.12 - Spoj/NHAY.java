package Tasks;

import javaUtils.AhoCorasickDataStructure;
import javaUtils.InReader;
import javaUtils.OutputWriter;

public class NHAY {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        try {
            while (true) {
                int l = in.readInt();
                String s = in.readString();
                AhoCorasickDataStructure ds = new AhoCorasickDataStructure(26, 'a');
                ds.add(s, 0);
                ds.bfs();
                ds.makeTrans();
                int q = 0;
                int c = in.read();
                int i = 0;
                while (c != '\n') {
                    q = ds.trans[q][c - 'a'];
                    if (ds.found[q]) out.printLine(i - l + 1);
                    i++;
                    c = in.read();
                }
                out.printLine('\n');
            }
        } catch (Exception e) {

        }
    }
}
