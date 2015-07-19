package Tasks;

import java.util.HashMap;

public class BigFatInteger2 {
    public String isDivisible(int A, int B, int C, int D) {
        HashMap<Integer, Long> map = new HashMap<Integer, Long>();
        HashMap<Integer, Long> mapc = new HashMap<Integer, Long>();
        for (int i = 2; i <= A / i; i++) {
            if (A % i == 0) {
                long cnt = 0;
                while (A % i == 0) {
                    cnt++;
                    A /= i;
                }
                map.put(i, cnt * B);
            }
        }
        if (A > 1) map.put(A, (long) B);


        for (int i = 2; i <= C / i; i++) {
            if (C % i == 0) {
                long cnt = 0;
                while (C % i == 0) {
                    cnt++;
                    C /= i;
                }
                mapc.put(i, cnt * D);
            }
        }
        if (C > 1) mapc.put(C, (long) D);

        for (int div : mapc.keySet()) {
            long exp2 = mapc.get(div);
            if (map.containsKey(div)) {
                long exp1 = map.get(div);
                if (exp1 < exp2) return "not divisible";
            } else return "not divisible";
        }
        return "divisible";

    }
}
