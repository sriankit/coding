package Tasks;

import javaUtils.*;

import java.util.Arrays;

/*
Mark has recently started studying string algorithms. So, as to gauge his knowledge, Lucy challenges him to a task.

"Given a string s, answer several times a query to determine whether a substring s[i,j] (inclusive) is palindromic or not.", said Lucy in a confident tone.!

As smart as Mark is, he was able to instantly find the solution!

Now, Mark has challenged little Lucy to do the same task by reversing a specific substring beforehand. As Lucy is still just a novice, she asks for your help.

You have to write a program that answers Q queries on a string S.

Each query contains four integers (i, j, k, l). For every query, first reverse the substring [k, l] (inclusive) and then report if substring [i, j] (inclusive) is a palindrome.

Note that the reversal operations are only for the specific query and should not persist for furthur queries.

Constraints:
1 ≤ |S| ≤ 10^5
1 ≤ |Q| ≤ 10^5
1 ≤ i ≤ j ≤ |S|
1 ≤ k ≤ l ≤ |S|

Input Format:
The first line of input file contains string S. The next line contains an integer Q.
Each of the following Q lines each contain 4 space separated integers i, j, k and l.

Output Format:
Output exactly Q lines, each containing the result of corresponding query as "Yes" or "No".
 */
public class PalindromicQueries {

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
        String s = new String(sarr);
        HashStore hash = new HashStore(sarr);

        int queryCount = in.readInt();
        for (int testIdx = 0; testIdx < queryCount; testIdx++) {
            int[] a = new int[]{2 * in.readInt(), 2 * in.readInt()};
            int[] b = new int[]{2 * in.readInt(), 2 * in.readInt()};
            // a is substring to be reversed and b is to be queried

            if(testIdx == 328) System.out.println(a[0] + " " + a[1] + " " + b[0] + " " + b[1]);

            long[] fhash = new long[2];
            long[] bhash = new long[2];

            if(a[1] < b[0] || a[0] > b[1]) {
                fhash = hash.getForwardHash(b[0], b[1]);
                bhash = hash.getBackwardHash(b[0], b[1]);
            }

            else if(a[0] <= b[0]) {
                //System.out.print("Case 1: ");
                int mid = a[0] + a[1] >> 1;
                int blen = 0;
                int st2 = b[0];
                StringBuilder buider = new StringBuilder();
                String rev = "";
                if(mid > b[0]) {
                    int start = 2 * mid - (mid <= b[1] ? mid : b[1]);
                    int end = 2 * mid - b[0];
                    int len =  end - start + 1;
                    HashStore.hashAppend(fhash, hash.getBackwardHash(start, end), len);
                    HashStore.hashPrepend(bhash, hash.getForwardHash(start, end), 0);
                    rev += s.substring(start, end + 1);
                    blen += len;
                    buider.append(MiscUtils.reverse(s, start, end));
                    st2 = mid + 1;
                }

                if(a[1] >= b[0]) {
                    int start = 2 * mid - a[1];
                    int end = 2 * mid - st2;
                    int len = end - start + 1;
                    HashStore.hashAppend(fhash, hash.getBackwardHash(start, end), len);
                    HashStore.hashPrepend(bhash, hash.getForwardHash(start, end), blen);
                    rev = s.substring(start, end + 1) + rev;
                    blen += len;
                    buider.append(MiscUtils.reverse(s, start, end));
                }
                if(b[1] > a[1]) {
                    int len = b[1] - a[1];
                    HashStore.hashAppend(fhash, hash.getForwardHash(a[1] + 1, b[1]), len);
                    HashStore.hashPrepend(bhash, hash.getBackwardHash(a[1] + 1, b[1]), blen);
                    buider.append(s.substring(a[1] + 1, b[1] + 1));
                    rev = MiscUtils.reverse(s, a[1] + 1, b[1]) + rev;
                    blen += len;
                }
                if(testIdx == 328) System.out.println(getTrueSubstring(s, a[0], a[1], b[0], b[1]) + " " + buider.toString() + " " + rev +" " + Arrays.toString(fhash) + " " + Arrays.toString(bhash));
            } else {
                //System.out.print("Case 2: ");
                int blen = 0;
                //String fs = "", bs = "";

                HashStore.hashAppend(fhash, hash.getForwardHash(b[0], a[0] - 1), a[0] - b[0]);
                HashStore.hashPrepend(bhash, hash.getBackwardHash(b[0], a[0] - 1), 0);
                blen += a[0] - b[0];

                //fs += s.substring(b[0], a[0]);
                //bs += MiscUtils.reverse(s, b[0], a[0] - 1);

                int mid = a[0] + a[1] >> 1;
                int st2 = b[1];

                if(mid < b[1]) {
                    st2 = mid - 1;
                }
                int start = 2 * mid - st2;
                int end = 2 * mid - a[0];
                HashStore.hashAppend(fhash, hash.getBackwardHash(start, end), end - start + 1);
                HashStore.hashPrepend(bhash, hash.getForwardHash(start, end), blen);
                blen += end - start + 1;

                //fs += s.substring(start, end + 1);
                //bs = MiscUtils.reverse(s, start, end) + bs;

                if(mid < b[1]) {
                    int mirror = 2 * mid - b[1];
                    HashStore.hashAppend(fhash, hash.getBackwardHash(mirror, mid), mid - mirror + 1);
                    HashStore.hashPrepend(bhash, hash.getForwardHash(mirror, mid), blen);

                    //fs += MiscUtils.reverse(s, mirror, mid);
                    //bs = s.substring(mirror, mid + 1) + bs;
                }

                //System.out.println(getTrueSubstring(s, a[0], a[1], b[0], b[1]) + " " + fs + " " + bs +" " + Arrays.toString(fhash) + " " + Arrays.toString(bhash));
            }
            boolean res = HashStore.compareHash(fhash, bhash);
            if(testIdx == 328) System.out.println("Done: " + testIdx + " " + getTrueSubstring(s, a[0], a[1], b[0], b[1]) + " " + res + " " + Arrays.toString(fhash) + " " + Arrays.toString(bhash));
            if(res) out.printLine("Yes");
            else out.printLine("No");

        }
    }
}
