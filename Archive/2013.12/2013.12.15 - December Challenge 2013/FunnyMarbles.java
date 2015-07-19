package Tasks;

import javaUtils.BIT;
import javaUtils.InReader;
import javaUtils.OutputWriter;

public class FunnyMarbles {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int q = in.readInt();
        BIT tree = new BIT(n + 1);
        for (int i = 1; i <= n; i++) {
            tree.update(i, in.readInt());
        }
        while (q-- > 0) {
            char ch = in.readCharacter();
            switch (ch) {
                case 'S':
                    int ind1 = in.readInt() + 1;
                    int ind2 = in.readInt() + 1;
                    out.printLine(tree.rangeFreq(ind1, ind2));
                    break;
                case 'G':
                    int ind = in.readInt() + 1;
                    int val = in.readInt();
                    tree.update(ind, val);
                    break;
                case 'T':
                    ind = in.readInt() + 1;
                    val = in.readInt();
                    tree.update(ind, -val);
                    break;
                default:
                    throw new RuntimeException();
            }
        }
    }
}
