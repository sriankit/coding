package Tasks;

public class BigFatInteger {
    public int minOperations(int A, int B) {
        int pr = 0;
        int maxe = 0;
        for (long i = 2; i <= A; i++) {
            if (A % i == 0) {
                System.out.println("i = " + i);
                pr++;
                int le = 0;
                while (A % i == 0) {
                    le++;
                    A /= i;
                }
                if (le > maxe) maxe = le;
            }
        }
        int ans = pr;
        maxe *= B;
        if (maxe == 1) return pr;
        int x = 1;
        while (x < maxe) {
            ans++;
            x *= 2;
        }
        System.out.println("val = " + " " + (32 - Integer.numberOfLeadingZeros(40) - 1));
        return ans;

    }
}
