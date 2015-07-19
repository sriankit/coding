package Tasks;

import javaUtils.HashStore;
import javaUtils.InReader;
import javaUtils.MiscUtils;
import javaUtils.OutputWriter;

import java.util.Arrays;

public class ANKPAL {

    long[] fhash, bhash;
    HashStore hash;
    String s, fs, bs;
    int HASH_CNT = 1;

    private void update(int start, int end, boolean forward, int backLen) {
        int len = end - start + 1;
        String add = s.substring(start, end + 1);
        if(forward) {
            HashStore.hashAppend(fhash, hash.getForwardHash(start, end), len);
            HashStore.hashPrepend(bhash, hash.getBackwardHash(start, end), backLen);
            //bs = MiscUtils.reverse(add) + bs;
            //fs += add;
        }
        else {
            HashStore.hashAppend(fhash, hash.getBackwardHash(start, end), len);
            HashStore.hashPrepend(bhash, hash.getForwardHash(start, end), backLen);
            //fs += MiscUtils.reverse(add);
            //bs = add + bs;
        }
    }

    // Transform s into t.
    // For example, if s = "abba", then t = "$#a#b#b#a#@"
    // the # are interleaved to avoid even/odd-length palindromes uniformly
    // $ and @ are prepended and appended to each end to avoid bounds checking
    private char[] preprocess(String s) {
        char[] t = new char[s.length() * 2 + 3];
        t[0] = '$';
        t[s.length() * 2 + 2] = '@';
        for (int i = 0; i < s.length(); i++) {
            t[2 * i + 1] = '#';
            t[2 * i + 2] = s.charAt(i);
        }
        t[s.length() * 2 + 1] = '#';
        return t;
    }

    private String getTrueSubstring(String str, int... a) {
        char[] s = str.toCharArray();
        int i = a[0], j = a[1];
        while(i < j) {
            char t = s[i];
            s[i] = s[j];
            s[j] = t;
            i ++;
            j --;
        }
        //System.out.println(new String(s));
        return new String(Arrays.copyOfRange(s, a[2], a[3] + 1));
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        char[] sarr = preprocess(in.readString());
        s = new String(sarr);
        hash = new HashStore(sarr);

        int queryCount = in.readInt();
        for (int testIdx = 0; testIdx < queryCount; testIdx++) {
            int[] a = new int[]{2 * in.readInt(), 2 * in.readInt()};
            int[] b = new int[]{2 * in.readInt(), 2 * in.readInt()};
            // a is substring to be reversed and b is to be queried

            fhash = new long[HASH_CNT];
            bhash = new long[HASH_CNT];

            if(a[1] < b[0] || b[1] < a[0]) {
                //No overlapping
                fhash = hash.getForwardHash(b[0], b[1]);
                bhash = hash.getBackwardHash(b[0], b[1]);
            }

            else {
                int mid = a[0] + a[1] >> 1;
                fs = "";
                bs = "";
                int backLen = 0;
                if (b[0] < a[0]) {
                    int start = b[0];
                    int end = a[0] - 1;
                    int len = end - start + 1;
                    update(start, end, true, 0);
                    backLen += len;
                    b[0] = a[0];
                }
                //if mid comes inside the b
                if (mid >= b[0]) {
                    int start = 2 * mid - Math.min(b[1], mid);
                    int end = 2 * mid - b[0];
                    int len = end - start + 1;
                    update(start, end, false, backLen);
                    backLen += len;
                }
                //if mid...b[1] is non-empty
                if (mid < b[1]) {
                    int start = 2 * mid - Math.min(a[1], b[1]);
                    int end = 2 * mid - Math.max(mid + 1, b[0]);
                    int len = end - start + 1;
                    update(start, end, false, backLen);
                    backLen += len;
                }
                //if a[1]...b[1] is non-empty
                if (a[1] < b[1]) {
                    int start = a[1] + 1;
                    int end = b[1];
                    update(start, end, true, backLen);
                }
            }
            boolean res = HashStore.compareHash(fhash, bhash);
            //System.out.println("debug info : " + a[0] + " " + a[1] + " " + b[0] + " " + b[1] + " " + getTrueSubstring(s, a[0], a[1], b[0], b[1]) + " " + fs + " " + bs + " " + res);
            if(res) out.printLine("Yes");
            else out.printLine("No");
        }

    }
}
