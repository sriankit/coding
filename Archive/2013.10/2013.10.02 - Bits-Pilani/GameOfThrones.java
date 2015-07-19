package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.MaxBIT;
import javaUtils.OutputWriter;

public class GameOfThrones {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int[] a = IOUtils.readIntArray(in, n);

        MaxBIT tree = new MaxBIT(n + 1);


        int ansInd = 0, ans = 1;

        for (int i = 0; i < n; i++) {
            int c = tree.getMax(a[i]);
            if (c != -1) {
                if (tree.tree[c] + 1 > ans) {
                    ansInd = i;
                    ans = (int) tree.tree[c] + 1;
                }
                tree.update(a[i], tree.tree[c] + 1);
            } else tree.update(a[i], 1);
        }

        out.printLine(ans);

    }
}
