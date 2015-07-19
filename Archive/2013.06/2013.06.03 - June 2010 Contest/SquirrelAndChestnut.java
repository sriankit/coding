package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.Arrays;

public class SquirrelAndChestnut {
    int tym[];
    int p[];
    int n, m;

    public void solve(int testNumber, InReader in, OutputWriter out) {
        testNumber = in.readInt();
        while (testNumber-- > 0) {
            m = in.readInt();
            n = in.readInt();
            int k = in.readInt();
            tym = new int[m];
            p = new int[m];

            for (int i = 0; i < m; i++) {
                tym[i] = in.readInt();
            }
            for (int i = 0; i < m; i++) {
                p[i] = in.readInt();
            }

            int min = tym[0] + (k - 1) * p[0];
            for (int i = 1; i < m; ++i)
                if (min > tym[i] + (k - 1) * p[i])
                    min = tym[i] + (k - 1) * p[i];

            int lo = 0;
            int hi = min;
            int ans = lo;
            while (lo <= hi) {
                int mid = lo + hi >> 1;
                if (total(mid) >= k) {
                    ans = mid;
                    hi = mid - 1;
                } else lo = mid + 1;
            }
            out.printLine(ans);
        }
    }

    private long total(int v) {
        int[] nuts = new int[m];
        for (int i = 0; i < m; i++) {
            if (v >= tym[i]) nuts[i] = 1 + (v - tym[i]) / p[i];
        }
        Arrays.sort(nuts);
        int j = n;
        long ret = 0;
        int i = m - 1;
        while (j-- > 0) {
            if (i < 0) break;
            ret += nuts[i--];
        }
        return ret;
    }
}
