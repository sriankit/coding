package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.MaxBIT;
import javaUtils.OutputWriter;

public class XMEN {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int[] a = IOUtils.readIntArray(in, n);
        int[] b = IOUtils.readIntArray(in, n);
        int[] ind = new int[n];
        for (int i = 0; i < n; i++) {
            ind[a[i] - 1] = i;
        }
        for (int i = 0; i < n; i++) {
            b[i] = ind[b[i] - 1];
        }

        for (int i = 0; i < n; i++) {
            ind[b[i]] = i;
        }

        int[] p = new int[n];

        MaxBIT tree = new MaxBIT(n + 1);

        int ansInd = 0, ans = 1;

        for (int i = 0; i < n; i++) {
            int c = tree.getMax(b[i] + 1);
            p[i] = (c == -1 ? -1 : ind[c - 1]);
            if (c != -1) {
                if (tree.tree[c] + 1 > ans) {
                    ansInd = i;
                    ans = (int) tree.tree[c] + 1;
                }
                tree.update(b[i] + 1, tree.tree[c] + 1);
            } else tree.update(b[i] + 1, 1);
        }

        out.printLine(ans);

        /*ArrayList<Integer> ansList = new ArrayList<Integer>();
        while (ansInd != -1) {
            ansList.add(b[ansInd]);
            ansInd = p[ansInd];
        }
        for (int i = ansList.size() - 1; i >= 0; i--) {
            out.print(ansList.get(i) + " ");

        }  */
    }
}
