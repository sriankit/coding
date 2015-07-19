package Tasks;

import java.util.Arrays;
import java.util.HashMap;

public class PiecewiseLinearFunction {
    public int maximumSolutions(int[] Y) {
        int n = Y.length;
        for (int i = 1; i < n; i++) {
            if (Y[i] == Y[i - 1]) return -1;
        }

        int ans = 0;
        for (int ele : Y) {
            int cnt = 0;
            for (int i = 0; i < n; i++) {
                if (Y[i] == ele) {
                    cnt++;
                    i++;
                    continue;
                } else if (i == 0) continue;
                int st = Math.min(Y[i], Y[i - 1]);
                int ed = Math.max(Y[i], Y[i - 1]);
                if (ele > st && ele < ed) cnt++;
            }
            ans = Math.max(ans, cnt);
        }

        HashMap<Integer, Integer> start = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> end = new HashMap<Integer, Integer>();

        for (int i = 1; i < n; i++) {
            int st = Math.min(Y[i], Y[i - 1]);
            int ed = Math.max(Y[i], Y[i - 1]);
            start.put(st, start.containsKey(st) ? start.get(st) + 1 : 1);
            end.put(ed, end.containsKey(ed) ? end.get(ed) + 1 : 1);
        }

        Arrays.sort(Y);
        int cur = 0;
        cur += start.containsKey(Y[0]) ? start.get(Y[0]) : 0;
        cur -= end.containsKey(Y[0]) ? end.get(Y[0]) : 0;
        for (int i = 1; i < Y.length; i++) {
            if (Y[i] == Y[i - 1]) continue;
            ans = Math.max(ans, cur);
            System.out.println("cur = " + cur);
            cur += start.containsKey(Y[i]) ? start.get(Y[i]) : 0;
            cur -= end.containsKey(Y[i]) ? end.get(Y[i]) : 0;
        }
        System.out.println("final cur = " + cur);
        assert (cur == 0);

        return ans;
    }
}