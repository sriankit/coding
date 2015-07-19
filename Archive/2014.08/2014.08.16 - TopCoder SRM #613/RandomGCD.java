package Tasks;

import javaUtils.IntegerUtils;

import java.util.Arrays;

public class RandomGCD {
    int get(int N, int K, int low, int high) {
        int exp = high / K - (low - 1) / K;
        return (int)IntegerUtils.power(exp, N, 1000000007);
    }
    public int countTuples(int N, int K, int low, int high) {
        int [] primes = IntegerUtils.generatePrimes(100005);
        int[] dist = new int[high - low + 1];
        int[] ok = new int[high - low + 1];
        Arrays.fill(ok, 1);
        for(int pr : primes) {
            for (int i = (low / pr) * pr; i <= high; i+=pr) {
                if(i % K == 0 && (i / K) % pr == 0) {
                    int ind = i - low;
                    if (ind >= 0) {
                        dist[ind]++;
                        ok[ind] *= pr;
                    }
                }
            }
        }
        long ans = 0;
        int MOD = 1000000007;

        for (int i = 0; i < high - low + 1; i++) {
            int num = i + low;
            if(num / K == ok[i] && num % K == 0) {
                int c = dist[i];
                int sgn = -1;
                if(c % 2 == 0) sgn = 1;
                ans += sgn * get(N, num, low, high);
                ans %= MOD;
                if(ans < 0) ans += MOD;
            }
        }
        return (int)ans;
    }
}
