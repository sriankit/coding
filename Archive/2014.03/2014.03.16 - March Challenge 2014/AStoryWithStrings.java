package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;
import javaUtils.SuffixArray;

import java.util.ArrayList;
import java.util.HashSet;

public class AStoryWithStrings {
    long MOD = (long) (1L << 18);

    public void solve(int testNumber, InReader in, OutputWriter out) {
        long P = 1000000007;
        long ppow[] = new long[300000];
        ppow[0] = 1;
        for (int i = 1; i < 300000; i++) {
            ppow[i] = (ppow[i - 1] * P) % MOD;
        }

        String present = in.readString();
        String future = in.readString();
        String s = future + "\1" + present;
        //System.out.println(s);
        //System.out.println(future.length());
        int[] SA = SuffixArray.suffixArray(s);
        int[] lcp = SuffixArray.lcp(SA, s);
        //System.out.println(Arrays.toString(SA));
        //System.out.println(Arrays.toString(lcp));
        ArrayList<Integer> indices = new ArrayList<Integer>();
        int ind = 0, len = 0;
        for (int i = 0; i < lcp.length; i++) {
            int i1 = SA[i];
            int i2 = SA[i + 1];
            int l = lcp[i];
            if (i1 < future.length() && i2 < future.length()) continue;
            if (i1 > future.length() && i2 > future.length()) continue;
            if (i1 > i2) i1 = i2 + (i2 = i1) * 0;

            {
                if (l > len) {
                    indices.clear();
                    indices.add(i1);
                    len = l;
                } else if (l == len) {
                    indices.add(i1);
                }
            }
        }
        int n = future.length();
        long[] hleft = new long[n];
        hleft[0] = future.charAt(0);
        for (int i = 1; i < n; i++) {
            hleft[i] = hleft[i - 1] * P;
            if (hleft[i] >= MOD) hleft[i] %= MOD;
            hleft[i] += future.charAt(i);
            if (hleft[i] >= MOD) hleft[i] -= MOD;
        }
        HashSet<Long> set = new HashSet<Long>();
        if (len > 0) {
            for (int index : indices) {
                long hash = (hleft[index + len - 1] - (index == 0 ? 0 : (hleft[index - 1] * ppow[len]) % MOD)) % MOD;
                if (hash < 0) hash += MOD;
                //System.out.println(index + s.substring(index, index + len) + " " + hash);
                set.add(hash);
            }
            for (int i = 0; i + len - 1 < future.length(); i++) {
                long hash = hleft[i + len - 1];
                if (i > 0) hash -= (hleft[i - 1] * ppow[len]) % MOD;
                hash %= MOD;
                if (hash < 0) hash += MOD;
                if (set.contains(hash)) {
                    out.printLine(future.substring(i, i + len));
                    break;
                }
            }
            out.printLine(len);
        } else out.printLine(0);
    }
}
