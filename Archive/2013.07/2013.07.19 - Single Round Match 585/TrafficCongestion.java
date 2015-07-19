package Tasks;

public class TrafficCongestion {
    long pow2[];
    long MOD = 1000000007;

    public int theMinCars(int treeHeight) {
        pow2 = new long[1000006];
        pow2[0] = 1;
        for (int i = 1; i < 1000006; i++) {
            pow2[i] = pow2[i - 1] * 2;
            if (pow2[i] >= MOD) pow2[i] %= MOD;
        }

        int n = treeHeight;
        long ans = 0;
        for (int i = n - 1; i >= 0; i -= 2) {
            ans += pow2[i];
            if (ans >= MOD) ans -= MOD;
        }
        if (treeHeight % 2 == 0) ans++;
        return (int) (ans % MOD);
    }
}
