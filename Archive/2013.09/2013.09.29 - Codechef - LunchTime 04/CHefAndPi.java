package Tasks;

import javaUtils.*;

public class CHefAndPi {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        testNumber = in.readInt();
        while (testNumber-- > 0) {
            int n = in.readInt();
            long[] a = IOUtils.readLongArray(in, n);
            AhoCorasickDataStructure trie = new AhoCorasickDataStructure(2, '0');
            long max = 0;
            int maxl = 0;

            for (int i = 0; i < n; i++) {
                maxl = Math.max(IntegerUtils.getBinary(a[i]).length(), maxl);
            }

            for (int k = 1; k < n; k++) {
                if (k >= 2) {
                    String bin = IntegerUtils.getBinary(a[k]);
                    StringBuilder builder = new StringBuilder();
                    for (int j = bin.length(); j < maxl + 1; j++) {
                        builder.append('0');
                    }
                    builder.append(bin);
                    long res = trie.getCloseMatch(builder.toString());
                    //System.out.println("res = " + res);
                    max = Math.max(res ^ a[k], max);
                }

                for (int i = 0; i < k; i++) {
                    long xor = a[i] ^ a[k];
                    String bin = IntegerUtils.getBinary(xor);
                    StringBuilder builder = new StringBuilder();
                    for (int j = bin.length(); j < maxl + 1; j++) {
                        builder.append('0');
                    }
                    builder.append(bin);
                    //System.out.println(builder.toString());
                    trie.add(builder.toString(), 1);
                }
            }

            /*for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    long xor = a[i] ^ a[j];
                    String bin = IntegerUtils.getBinary(xor);
                    StringBuilder builder = new StringBuilder();
                    for (int k = bin.length(); k < 40; k++) {
                        builder.append('0');
                    }
                    builder.append(bin);
                    trie.add(builder.toString(), 1);
                    if(j + 1 < n) {
                        bin = IntegerUtils.getBinary(a[j + 1]);
                        long res = trie.getCloseMatch(bin);
                        max = Math.max(res ^ a[j + 1], max);
                    }
                }
            }



            for (int i = 0; i < n; i++) {
                String bin = IntegerUtils.getBinary(a[i]);
                long res = trie.getCloseMatch(bin);
                max = Math.max(res ^ a[i], max);
            }                  */
            out.printLine(max);
        }
    }
}
