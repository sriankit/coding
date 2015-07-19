package Tasks;

import javaUtils.ArrayUtils;
import javaUtils.CollectionUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.ArrayList;

public class Scales {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int[] ternary = new int[22];
        int n = in.readInt();
        long x = in.readLong();
        int i = 0;
        while (x > 0) {
            ternary[i++] = (int) (x % 3);
            x /= 3;
        }
        int carry = 0;
        ArrayList<Integer> arr1 = new ArrayList<Integer>();
        ArrayList<Integer> arr2 = new ArrayList<Integer>();
        //System.out.println(Arrays.toString(ternary));
        for (int j = 0; j < n; j++) {
            ternary[j] = ternary[j] + carry;
            if (ternary[j] == 0 || ternary[j] == 1) carry = 0;
            else {
                if (ternary[j] == 2) {
                    arr1.add(j + 1);
                    carry = 1;
                    ternary[j] = 0;
                } else {
                    ternary[j] = 0;
                    carry = 1;
                }
            }
        }
        ternary[n] += carry;
        //System.out.println(Arrays.toString(ternary));

        boolean done = true;

        for (int j = 0; j < 22; j++) {
            assert (ternary[j] != 3);
            if (j >= n && ternary[j] != 0) done = false;
            if (ternary[j] == 1) arr2.add(j + 1);
            else if (ternary[j] == 2) done = false;
        }
        if (!done) out.printLine(-1);
        else {
            ArrayUtils.printArr(out, CollectionUtils.toArray(arr1));
            ArrayUtils.printArr(out, CollectionUtils.toArray(arr2));
        }
    }
}
