package Tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class AlienAndHamburgers {

    int n;
    int store[];
    int dp[][][];

    int go(int i, int sel, int sum) {
        if (i == n) return sel * sum;
        if (dp[i][sel][sum] == -1)
            dp[i][sel][sum] = Math.max(go(i + 1, sel, sum), go(i + 1, sel + 1, sum + store[i]));
        return dp[i][sel][sum];
    }

    public int getNumber(int[] type, int[] taste) {
        HashMap<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
        for (int i = 0; i < type.length; i++) {
            if (map.containsKey(type[i])) {
                ArrayList<Integer> tmp = map.get(type[i]);
                tmp.add(taste[i]);
                map.put(type[i], tmp);
            } else {
                ArrayList<Integer> tmp = new ArrayList<Integer>();
                tmp.add(taste[i]);
                map.put(type[i], tmp);
            }
        }
        n = map.size();
        store = new int[n];
        int ind = 0;
        for (ArrayList<Integer> arr : map.values()) {
            Collections.sort(arr);
            System.out.println(arr);
            int s = 0;
            for (int i = arr.size() - 1; i >= 0; i--) {
                if (i == arr.size() - 1 || arr.get(i) > 0) s += arr.get(i);
                else break;
            }
            store[ind++] = s;
        }
        //ArrayUtils.fill3d(dp, -1);
        System.out.println(Arrays.toString(store));
        Arrays.sort(store);
        //n = ind;
        int y = 0;
        int a = 0;
        for (int i = n - 1; i >= 0; i--) {
            if ((a + store[i]) * (y + 1) > a * y) {
                y++;
                a += store[i];
            } else break;
        }
        return a * y;
    }

}

