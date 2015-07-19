package Tasks;

import javaUtils.ArrayUtils;
import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.Arrays;

public class WarZone {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int t = in.readInt();
        while (t-- > 0) {
            int n = in.readInt();
            int[] arr = IOUtils.readIntArray(in, n);
            //int mini = ArrayUtils.minPosition(arr);
            int maxi, mini;

            maxi = ArrayUtils.maxPosition(arr);
            int tmp = arr[maxi];
            arr[maxi] = Integer.MIN_VALUE;
            mini = ArrayUtils.maxPosition(arr);
            arr[maxi] = tmp;
            boolean rev = false;
            if (mini > maxi) {
                rev = true;
                for (int i = 0, j = n - 1; i < j; i++, j--) {
                    arr[i] = arr[j] + (arr[j] = arr[i]) * 0;
                }
                maxi = n - maxi - 1;
                mini = n - mini - 1;
            }

            if (mini < maxi) {
                while (mini > 0) {
                    arr[mini] = arr[mini - 1] + (arr[mini - 1] = arr[mini]) * 0;
                    mini--;
                }
                while (maxi < n - 1) {
                    arr[maxi] = arr[maxi + 1] + (arr[maxi + 1] = arr[maxi]) * 0;
                    maxi++;
                }
                //ArrayUtils.printArr(out, arr);
                int k = (n - 2) / 3;
                Arrays.sort(arr, 0, k + 1);
                Arrays.sort(arr, n - k - 1, n);
                for (int i = n - k - 1, j = n - 1; i < j; i++, j--) {
                    arr[i] = arr[j] + (arr[j] = arr[i]) * 0;
                }
                if (rev)
                    for (int i = 0, j = n - 1; i < j; i++, j--) {
                        arr[i] = arr[j] + (arr[j] = arr[i]) * 0;
                    }

                for (int i = 0; i < n; i++) {
                    if (i < k + 1 || i >= n - k - 1) out.print(arr[i] + " ");
                    else out.print("0 ");
                }
            }

        }
    }
}
