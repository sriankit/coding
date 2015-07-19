package Tasks;

public class YetAnotherIncredibleMachine {
    long MOD = 1000000009;

    public int countWays(int[] platformMount, int[] platformLength, int[] balls) {
        int platCount = platformLength.length;
        int ballCount = balls.length;
        boolean fail = false;
        long prod = 1;
        for (int i = 0; i < platCount; i++) {
            int o = platformMount[i];
            int s = o - platformLength[i];
            int e = o + platformLength[i];
            for (int j = 0; j < ballCount; j++) {
                System.out.println(" s =  " + s + "  e = " + e);
                if (balls[j] < o) {
                    s = Math.max(s, balls[j] + 1);
                    System.out.println("in s =  " + s + "  e = " + e + " balls[j] + 1 = " + (balls[j] + 1));
                } else if (balls[j] == o) {
                    fail = true;
                } else {
                    e = Math.min(e, balls[j] - 1);
                }
            }
            System.out.println(" s =  " + s + "  e = " + e);
            if (e - s < platformLength[i]) fail = true;
            else {
                prod *= e - s + 1 - platformLength[i];
                if (prod >= MOD) prod %= MOD;
            }
        }
        if (fail) return 0;
        else return (int) prod;
    }
}
