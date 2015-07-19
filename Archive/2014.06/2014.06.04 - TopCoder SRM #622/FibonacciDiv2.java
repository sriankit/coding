package Tasks;

public class FibonacciDiv2 {
    public int find(int N) {
        int[] fib = new int[100];
        fib[0] = 0;
        fib[1] = 1;
        for (int i = 2; i < 37; i++) {
            fib[i] = fib[i - 1] + fib[i - 2];
        }
        int ans = N;
        for (int i = 0; i < 37; i++) {
            ans = Math.min(ans, Math.abs(N - fib[i]));
        }
        return ans;
    }
}
