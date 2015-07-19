package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.Arrays;

public class SaveThePrincess {
    int N;
    int total;
    int[] arr;
    double prob[];
    double[][] dp;

    double find_ans(int i, int s) {
        if (i == N) {
            if (s >= total - s) {
                //System.out.println("got " + s);
                return 1;
            } else return 0;
        }

        if (dp[i][s] != -1) return dp[i][s];

        return dp[i][s] = prob[i] * find_ans(i + 1, s + arr[i]) + (1 - prob[i]) * find_ans(i + 1, s);

    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        N = in.readInt();
        arr = IOUtils.readIntArray(in, N);
        total = 0;
        for (int i = 0; i < N; i++) {
            total += arr[i];
        }
        prob = new double[N];
        for (int i = 0; i < N; i++) {
            prob[i] = in.readInt() / 100.0;
        }
        dp = new double[N][total + 1];
        for (int i = 0; i < N; i++) {
            Arrays.fill(dp[i], -1);
        }
        out.printLine(find_ans(0, 0));
        //System.out.println(Arrays.deepToString(dp));
    }
}
