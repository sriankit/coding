package Tasks;

import javaUtils.ArrayUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

public class ARITHSEQ {
    int d, x;
    int pow10[];
    int req;

    public void solve(int testNumber, InReader in, OutputWriter out) {
        int a = in.readInt();
        d = in.readInt();
        long n = in.readLong();
        x = in.readInt();
        req = a % d;
        pow10 = new int[20];
        pow10[0] = 1;
        for (int i = 1; i < 20; i++) {
            pow10[i] = (pow10[i - 1] * 10) % d;
        }
        long ans = compute(a + n * d) - compute(a - 1);
        out.printLine(ans);
    }

    private long compute(long n) {
        if (n == 0) return 0;
        int[] arr = new int[20];
        long res = n;
        int cnt = 0;
        while (res > 0) {
            arr[cnt++] = (int) (res % 10);
            res /= 10;
        }
        long cntdp[][] = new long[cnt + 1][d];
        long xcntdp[][] = new long[cnt + 1][d];
        int lim = 9;
        if (cnt == 1) lim = (int) n;
        for (int i = 1; i <= lim; i++) {
            cntdp[1][i % d]++;
            if (i == x) xcntdp[1][i % d]++;
        }

        for (int len = 1; len < cnt - 1; len++) {
            for (int k = 0; k < d; k++) {
                for (int dig = 0; dig < 10; dig++) {
                    int j = (k * 10 + dig) % d;
                    cntdp[len + 1][j] += cntdp[len][k];
                    xcntdp[len + 1][j] += xcntdp[len][k];
                    if (dig == x) xcntdp[len + 1][j] += cntdp[len][k];
                }
            }
        }

        long ans = 0;
        for (int i = 1; i < cnt; i++) {
            ans += xcntdp[i][req];
        }


        //now include zero and do full redo
        ArrayUtils.fill2d(cntdp, 0);
        ArrayUtils.fill2d(xcntdp, 0);

        for (int i = 0; i <= lim; i++) {
            cntdp[1][i % d]++;
            if (i == x) xcntdp[1][i % d]++;
        }
        for (int len = 1; len < cnt - 1; len++) {
            for (int k = 0; k < d; k++) {
                for (int dig = 0; dig < 10; dig++) {
                    int j = (k * 10 + dig) % d;
                    cntdp[len + 1][j] += cntdp[len][k];
                    xcntdp[len + 1][j] += xcntdp[len][k];
                    if (dig == x) xcntdp[len + 1][j] += cntdp[len][k];
                }
            }
        }

        int mod = 0;
        int xs = 0;
        for (int i = cnt - 1; i > 0; i--) {
            for (int dig = i == cnt - 1 ? 1 : 0; dig < arr[i]; dig++) {
                for (int k = 0; k < d; k++) {
                    int j = (k + dig * pow10[i]) % d;
                    j = (j + mod) % d;
                    cntdp[cnt][j] += cntdp[i][k];
                    xcntdp[cnt][j] += xcntdp[i][k];
                    xcntdp[cnt][j] += xs * cntdp[i][k];
                    if (dig == x) xcntdp[cnt][j] += cntdp[i][k];
                }
            }
            mod = (mod + arr[i] * pow10[i]) % d;
            if (arr[i] == x) xs++;
        }
        //System.out.println("xs = " + xs);
        for (int dig = 0; dig < arr[0] && cnt > 1; dig++) {
            int j = (mod + dig) % d;
            cntdp[cnt][j]++;
            xcntdp[cnt][j] += xs;
            if (dig == x) xcntdp[cnt][j]++;
        }
        mod = (mod + arr[0]) % d;

        cntdp[cnt][mod]++;
        xcntdp[cnt][mod] += xs;
        if (arr[0] == x) xcntdp[cnt][mod]++;

        ans += xcntdp[cnt][req];
        //System.out.println(Arrays.deepToString(xcntdp));
        if (cnt == 1 && x == 0) ans--;
        System.out.println("ans = " + ans + "  for  n = " + n);
        return ans;
    }
}
