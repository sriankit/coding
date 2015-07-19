package Tasks;

import sun.tracing.ProbeSkeleton;

public class FixedDiceGameDiv2 {
    public double getExpectation(int a, int b) {
        double pwin = 0.0;
        for (int i = 2; i <= a; i++) {
            pwin += (1.0 / a) * (Math.min(b, i - 1) / (double)b);
        }
        System.out.println(pwin);
        double ans = 0;
        for (int i = 2; i <= a ; i++) {
            double prob = 1.0 / a;
            double mx = Math.min(i - 1, b);
            double win = mx / b;
            ans += i * prob * win / pwin;
        }
        return ans;
    }
}
