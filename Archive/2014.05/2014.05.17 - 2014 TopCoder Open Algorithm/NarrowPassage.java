package Tasks;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

public class NarrowPassage {
    public int minDist(int L, final int[] a, final int[] b) {
        int n = a.length;
        Integer[] ini = new Integer[n];
        Integer[] fi = new Integer[n];
        for (int i = 0; i < n; i++) {
            ini[i] = fi[i] = i;
        }
        Arrays.sort(ini, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return a[o1] - a[o2];
            }
        });
        Arrays.sort(fi, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return b[o1] - b[o2];
            }
        });
        int ans = Integer.MAX_VALUE;
        for (int f = 0; f <= n; f++) {
            for (int s = 0; s <= n; s++) {
                boolean ok = true;
                TreeSet<Integer> chp = new TreeSet<Integer>();
                for (int i = 0; i < f; i++) {
                    chp.add(ini[i]);
                }
                for (int i = 0; i < f; i++) {
                    if (!chp.contains(fi[i])) ok = false;
                }
                for (int i = f; i < s; i++) {
                    if (!ini[i].equals(fi[i])) ok = false;
                }
                chp.clear();
                for (int i = s; i < n; i++) {
                    chp.add(ini[i]);
                }
                for (int i = s; i < n; i++) {
                    if (!chp.contains(fi[i])) ok = false;
                }
                chp.clear();


                if (!ok) {
                    System.out.println("reject " + f + " " + s);
                    continue;
                }

                int challenge = 0;
                for (int i = 0; i < f; i++) {
                    challenge += b[ini[i]] + a[ini[i]];
                }
                for (int i = f; i < s; i++) {
                    challenge += Math.abs(b[ini[i]] - a[ini[i]]);
                }
                for (int i = s; i < n; i++) {
                    challenge += L - b[ini[i]] + L - a[ini[i]];
                }
                if (challenge < ans) {
                    ans = challenge;
                }
                System.out.println(f + " " + s + " " + challenge);
            }
        }
        return ans;
    }
}
