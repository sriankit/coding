package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

public class PAL {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        testNumber = in.readInt();
        while (testNumber-- > 0) {
            int n = in.readInt();
            int[] str = new int[32];
            for (int i = 31; i >= 0; i--) {
                str[i] = n % 2;
                n >>= 1;
            }
            //System.out.println(Arrays.toString(str));
            int max = 0;
            for (int i = 0; i < 32; i++) {
                int j;
                for (j = 0; i - j >= 0 && i + j < 32; j++) {
                    if (str[i - j] != str[i + j]) break;
                }
                max = Math.max(max, 2 * (j - 1) + 1);
                for (j = 0; i - j - 1 >= 0 && i + j < 32; j++) {
                    if (str[i - j - 1] != str[i + j]) break;
                }
                max = Math.max(max, 2 * (j - 1) + 2);
            }
            out.printLine(max);
        }
    }
}
