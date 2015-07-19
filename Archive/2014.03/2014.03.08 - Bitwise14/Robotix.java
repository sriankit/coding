package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;
import javaUtils.SuffixArray;

public class Robotix {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        String s = in.readString();
        int[] sa = SuffixArray.suffixArray(s);
        int l = s.length();
        long ind = in.readInt();
        //System.out.println(Arrays.toString(sa));
        for (int i : sa) {
            int end = i;
            boolean br = false;
            while (end < l) {
                ind -= end - i + 1;
                if (ind <= 0) {
                    out.printLine(s.charAt((int) (end - ind)));
                    br = true;
                    break;
                }
                end++;
            }
            if (br) break;
        }
    }
}
