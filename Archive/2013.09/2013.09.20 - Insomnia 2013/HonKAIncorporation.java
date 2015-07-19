package Tasks;

import javaUtils.ArrayUtils;
import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

public class HonKAIncorporation {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        String s = "1346";
        testNumber = in.readInt();
        while (testNumber-- > 0) {
            int n = in.readInt();
            int[] a = IOUtils.readIntArray(in, n);
            //Arrays.sort(a);
            boolean type1 = false, type2 = false, thief = false;
            for (int i = 0; i < n; i++) {
                if (a[i] <= 0) thief = true;
                else if (a[i] < 10) type1 = true;
                else if (a[i] > 36) {
                    if (a[i] > 96) thief = true;
                    else {
                        char c = (char) (a[i] % 10 + '0');
                        if (s.contains(c + "")) type2 = true;
                        else thief = true;
                    }
                } else {
                    char c = (char) (a[i] % 10 + '0');
                    if (!s.contains(c + "")) type1 = true;
                }
            }
            if (thief || (type1 && type2)) out.printLine("THIEF");
            else {
                if ((!type1 && !type2)) out.printLine("UNCLASSIFIABLE");
                else if (type2) ArrayUtils.printArr(out, a);
                else {
                    for (int i = 0; i < n; i++) {
                        int f = a[i] / 4;
                        int b = a[i] % 4;
                        a[i] = ((a[i] - 1) / 4 + 1) * 10 + (s.charAt((a[i] - 1) % 4) - '0');
                    }
                    ArrayUtils.printArr(out, a);
                }
            }
        }
    }
}
