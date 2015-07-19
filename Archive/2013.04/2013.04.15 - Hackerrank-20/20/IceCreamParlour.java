package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.Arrays;
import java.util.Comparator;

public class IceCreamParlour {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int t = in.readInt();
        while (t-- > 0) {
            int money = in.readInt();
            int n = in.readInt();
            int[][] arr = new int[n][2];
            for (int i = 0; i < n; i++) {
                arr[i][0] = in.readInt();
                arr[i][1] = i + 1;
            }
            Arrays.sort(arr, new Comparator<int[]>() {
                @Override
                public int compare(int[] o1, int[] o2) {
                    int k = o1[0] - o2[0];
                    if (k != 0) return k;
                    return o1[1] - o2[1];
                }
            });
            int fp = 0, bp = n - 1;
            while (fp < bp) {
                //while (arr[fp][0] == arr[bp][0]) fp++;
                int s = arr[fp][0] + arr[bp][0];
                if (s < money) {
                    fp++;
                } else if (s > money) {
                    bp--;
                } else {
                    int[] ans = new int[]{arr[fp][1], arr[bp][1]};
                    Arrays.sort(ans);
                    out.printLine(ans, 5);
                    break;
                }
            }
        }
    }
}
