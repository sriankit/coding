package Tasks;

import javaUtils.*;

import java.util.*;

public class RotationsAndInversions {
    int [] a;

    private int compress() {
        List<Integer> b = new ArrayList<Integer>();
        for (int i = 0; i < a.length; i++) {
            b.add(a[i]);
        }
        Collections.sort(b);
        int [] ba = ArrayUtils.unique(CollectionUtils.toArray(b), 0, b.size());
        for (int i = 0; i < a.length; i++) {
            a[i] = Arrays.binarySearch(ba, a[i]) + 1;
        }
        //System.out.println(Arrays.toString(a));
        return ba.length;
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        a = IOUtils.readIntArray(in, n);
        int maxval = compress();
        //two pointer approach with BIT
        BIT tree = new BIT(maxval);
        int i = 0, j = 0;
        long ans = -1, inv = 0;
        ArrayList<Integer> good = new ArrayList<>();
        while (i < n && j < 2 * n) {
            if (i > 0) {
                //remove the first index of last window
                inv -= tree.freqTo(a[i - 1] - 1);
                tree.update(a[i - 1], -1);
            }
            //include the new index of window
            tree.update(a[j % n], 1);
            //update the number of inversions
            //j - i + 1 gets the size of current window
            inv += j - i + 1 - tree.freqTo(a[j % n]);

            //if current window has size atleast n
            if (j >= n - 1) {
                if (ans == -1 || inv == ans) {
                    good.add(i);
                    ans = inv;
                } else if (inv > ans) {
                    //found better answer
                    good.clear();
                    good.add(i);
                    ans = inv;
                }
                //System.out.println(i + " " + j + " " + inv);
                i++;
            }
            j++;
        }
        boolean isGood[] = new boolean[2 * n];
        for (int goods : good)
            isGood[goods] = true;

        //System.out.println(Arrays.toString(isGood));

        int a2[] = new int[2 * n];
        for (int k = 0; k < n; k++) {
            a2[k] = a2[k + n] = a[k];
        }
        int[] sa = SuffixArray.suffixArray(a2);
        int[] lcp = SuffixArray.lcp(sa, a2);

        int ind = -1;
        //System.out.println(Arrays.toString(sa));
        //System.out.println(Arrays.toString(lcp));
        for (int idx = 0; idx < 2 * n; idx++) {
            if(!isGood[sa[idx]]) continue;
            ind = sa[idx];
            if(idx < 2 * n - 1) if(lcp[idx] < n) break;
        }
        out.printLine(ind + " " + ans);
    }
}
