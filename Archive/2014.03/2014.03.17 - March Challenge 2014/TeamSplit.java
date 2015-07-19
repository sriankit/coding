package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

public class TeamSplit {
    final int MOD = 1000000;

    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        long a = in.readInt();
        long b = in.readInt();
        long c = in.readInt();
        long d = in.readInt();
        long[] s = new long[n];
        int[] cnt = new int[(int) MOD];
        s[0] = d;
        int ind = 1;
        int mark[] = new int[(int) MOD];
        mark[(int) d] = ind++;
        cnt[((int) d)] = 1;
        int i;
        int period = 1;
        for (i = 1; i < n; i++) {
            long f1 = a * s[i - 1] * s[i - 1];
            if (f1 >= MOD) f1 %= MOD;
            f1 += b * s[i - 1];
            if (f1 >= MOD) f1 %= MOD;
            f1 += c;
            if (f1 >= MOD) f1 -= MOD;
            s[i] = f1;
            if (mark[(int) s[i]] > 0) {
                period = ind - mark[(int) s[i]];
                break;
            } else {
                mark[(int) s[i]] = ind++;
                cnt[((int) s[i])] = 1;
            }
        }

        int add = (n - i) / period;
        int start = i - period;
        for (int j = start; j < i; j++) {
            cnt[((int) s[j])] += add;
        }
        int go = (n - i) % period;
        for (int j = start; go > 0; j++, go--) {
            cnt[((int) s[j])]++;
        }

        long diff = 0;
        int sgn = 1;
        for (int num = 0; num < MOD; num++) {
            if (cnt[num] % 2 == 0) continue;
            if (sgn == 0) diff -= num;
            else diff += num;
            sgn ^= 1;
        }
        out.printLine(Math.abs(diff));
    }

}
