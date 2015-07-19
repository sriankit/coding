package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.Arrays;

public class TaskC {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int[] arr = IOUtils.readIntArray(in, n);
        Arrays.sort(arr);
        //System.out.println(Arrays.toString(arr));
        int j = n - 1;
        long ans = 0;
        long num = 1;
        long pre = 0;
        long preAdd = 0;
        while (j >= 0) {
            //System.out.println(j);
            long pans = ans;
            ans += preAdd;
            int cnt = 0;
            while (j >= 0 && cnt < num - pre) {
                ans += arr[j];
                //System.out.println(j + " ");
                j--;
                cnt++;
            }
            preAdd = ans - pans;
            pre = num;
            num *= 4;
        }
        out.printLine(ans);
    }
}
