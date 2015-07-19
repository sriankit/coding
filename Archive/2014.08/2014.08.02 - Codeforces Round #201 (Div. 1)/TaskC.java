package Tasks;

import com.sun.scenario.effect.Offset;
import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.Arrays;

public class TaskC {
    int [] sieve;
    int [] dp;
    int a, b;
    public int go(int i) {
        if(b + i >= a) return 0;
        if(dp[i] != -1) return dp[i];
        if(sieve[i] == -1) {
            return 1 + go(i + 1);
        }
        int mn = 1 + go(i + 1);
        for (int j = 1; j < sieve[i]; j++) {
            mn = Math.min(mn, 1 + go(i + j));
        }
        return dp[i] = mn;
    }

    public void solve(int testNumber, final InReader in, final OutputWriter out) {
        Thread t = new Thread(null, new Runnable() {
            @Override
            public void run() {
                solveIt(in, out);
            }
        },"oh yeah",1<<28);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void solveIt(InReader in, OutputWriter out) {
        int n = in.readInt();
        int [] x = IOUtils.readIntArray(in, n);
        a = in.readInt();
        b = in.readInt();
        assert ( a > b );
        sieve = new int[a - b + 1];
        Arrays.fill(sieve, -1);
        for (int aX : x) {
            for (int st = (b / aX) * aX; st < a + 1; st += aX) {
                if (st - b >= 0) sieve[st - b] = Math.max(sieve[st - b], aX);
            }
        }
        dp = new int[a - b + 1];
        Arrays.fill(dp, -1);
        out.printLine(go(0));
    }
}
