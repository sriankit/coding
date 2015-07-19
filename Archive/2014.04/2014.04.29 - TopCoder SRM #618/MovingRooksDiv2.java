package Tasks;

import java.util.Arrays;
import java.util.TreeMap;

public class MovingRooksDiv2 {
    int[] target;
    TreeMap<Long, Boolean> store;
    boolean pos = false;

    boolean go(int[] a) {
        long hashVal = 0;
        long MAX = (long) (1e18 + 3);
        for (int i = 0; i < a.length; i++) {
            hashVal = (hashVal * 1045564007) % MAX + a[i];
        }

        if (Arrays.equals(a, target)) {
            store.put(hashVal, true);
            return true;
        }
        if (store.containsKey(hashVal)) return store.get(hashVal);
        int[] b = (int[]) a.clone();
        for (int i = 0; i < b.length; i++) {
            for (int j = i + 1; j < b.length; j++) {
                if (b[i] > b[j]) {
                    b[i] = b[j] + (b[j] = b[i]) * 0;
                    boolean res = go(b);
                    if (res) {
                        store.put(hashVal, true);
                        return true;
                    }
                    b[i] = b[j] + (b[j] = b[i]) * 0;
                }
            }
        }
        store.put(hashVal, false);
        return false;
    }

    public String move(int[] Y1, int[] Y2) {
        target = Y2;
        store = new TreeMap<Long, Boolean>();
        pos = go(Y1);
        return pos ? "Possible" : "Impossible";
    }
}
