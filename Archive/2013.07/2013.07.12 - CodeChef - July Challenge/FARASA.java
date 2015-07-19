package Tasks;

import javaUtils.FastFourierTransform;
import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.HashSet;

public class FARASA {
    long[] pre;

    long getPreSum(int index) {
        if (index < 0) return 0;
        else return pre[index];
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int[] arr = IOUtils.readIntArray(in, n);
        pre = new long[n];
        pre[0] = arr[0];
        for (int i = 1; i < n; i++) {
            pre[i] = pre[i - 1] + arr[i];
        }
        if (n < 10000) {
            HashSet<Integer>[] save = new HashSet[100009];
            int MOD1 = 100005, MOD2 = 10000007;
            int cnt = 0;
            for (int i = 0; i < n; i++) {
                for (int j = i; j < n; j++) {
                    long sum = getPreSum(j) - getPreSum(i - 1);
                    int res1 = (int) (sum % MOD1);
                    int res2 = (int) (sum % MOD2);
                    if (save[res1] == null) {
                        save[res1] = new HashSet<Integer>();
                        save[res1].add(res2);
                        cnt++;
                    } else if (!save[res1].contains(res2)) {
                        save[res1].add(res2);
                        cnt++;
                    }
                }
            }
            out.printLine(cnt - 1);
        } else {
            int totalS = (int) pre[n - 1];
            long[] poly1 = new long[totalS + 1];
            long[] poly2 = new long[totalS + 1];
            for (int i = 0; i < n; i++) {
                poly1[(int) pre[i]]++;
                poly2[totalS - (int) pre[i]]++;
            }
            poly1[0]++;
            poly2[totalS]++;
            long[] res = FastFourierTransform.multiply(poly1, poly2);
            //System.out.println(Arrays.toString(res));
            long cnt = 0;
            for (int i = totalS + 1; i <= 2 * totalS; i++) {
                if (res[i] > 0) cnt++;
            }
            out.print(cnt - 1);
        }
    }
}
