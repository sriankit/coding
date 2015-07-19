package Tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class LCMSetEasy {
    public String include(int[] S, int x) {
        ArrayList<Integer> divs = new ArrayList<Integer>();
        Arrays.sort(S);
        for (int num : S) {
            if (x % num == 0) divs.add(num);
        }
        if (divs.isEmpty()) return "Impossible";
        ArrayList<Integer> facs = new ArrayList<Integer>();
        ArrayList<Integer> pows = new ArrayList<Integer>();
        for (int a = 1; a <= x / a; a++) {
            if (x % a == 0) {
                facs.add(a);
                facs.add(x / a);
            }
        }
        Collections.sort(facs);
        System.out.println(facs);
        System.out.println(divs);
        boolean f = false;
        for (int lcm : facs) {
            boolean fail = false;
            for (int num : divs) {
                if (lcm % num != 0) fail = true;
            }
            if (!fail && lcm != x) {
                f = true;
                break;
            }

        }
        if (f) return "Impossible";
        else return "Possible";
    }
}
