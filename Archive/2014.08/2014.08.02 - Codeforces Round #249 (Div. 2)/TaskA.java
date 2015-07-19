package Tasks;

import javaUtils.ArrayUtils;
import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.Arrays;

public class TaskA {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int [] a = IOUtils.readIntArray(in, n);
        Arrays.sort(a);
        for (int i = 0, j = n - 1; i < j; i++, j--) {
            int t = a[i];
            a[i] = a[j];
            a[j] = t;
        }
        //System.out.println(Arrays.toString(a));
        if(n > 2) Arrays.sort(a, 1, n - 1);
        int [] output = new int[n];

        ArrayUtils.printArr(out, a);
    }
}
