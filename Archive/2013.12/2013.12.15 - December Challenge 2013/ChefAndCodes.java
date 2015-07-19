package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.Arrays;
import java.util.Comparator;

public class ChefAndCodes {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int t = in.readInt();
        while (t-- > 0) {
            String seq = in.readString();
            char[] cipher = in.readLine().toCharArray();
            Character[] order = new Character[26];
            for (int i = 0; i < 26; i++) {
                order[i] = (char) ('a' + i);
            }
            final int freq[] = new int[26];
            for (char c : cipher) {
                if ('a' <= c && c <= 'z') freq[c - 'a']++;
                if ('A' <= c && c <= 'Z') freq[c - 'A']++;
            }
            Arrays.sort(order, new Comparator<Character>() {
                @Override
                public int compare(Character o1, Character o2) {
                    if (freq[o1 - 'a'] == freq[o2 - 'a']) return o1 < o2 ? -1 : 1;
                    else return freq[o1 - 'a'] - freq[o2 - 'a'];
                }
            });

            char[] map = new char[256];
            for (int i = 0; i < 256; i++) {
                map[i] = (char) (i);
            }
            for (int i = 0; i < order.length; i++) {
                map[order[i]] = seq.charAt(i);
                map[order[i] - 32] = (char) (seq.charAt(i) - 32);
            }

            StringBuilder builder = new StringBuilder();
            for (char c : cipher) {
                builder.append(map[c]);
            }
            out.printLine(builder.toString());

        }
    }
}
