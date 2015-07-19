package Tasks;

import javaUtils.Pair;

import java.util.Arrays;
import java.util.Comparator;

public class CatchTheBeatEasy {
    public String ableToCatchAll(int[] x, int[] y) {
        Pair<Integer, Integer> arr[] = new Pair[x.length];
        int n = x.length;
        for (int i = 0; i < n; i++) {
            arr[i] = Pair.makePair(x[i], y[i]);
        }
        Arrays.sort(arr, new Comparator<Pair<Integer, Integer>>() {
            @Override
            public int compare(Pair<Integer, Integer> o1, Pair<Integer, Integer> o2) {
                if (o1.second == o2.second) return o1.first - o2.first;
                else return o1.second - o2.second;
            }
        });
        int xi = 0, yi = 0, sub = 0;
        boolean f = false;
        for (int i = 0; i < n; i++) {
            if (Math.abs(xi - arr[i].first) <= arr[i].second - sub) {
                xi = arr[i].first;
                sub += arr[i].second - sub;
            } else f = true;
        }
        if (f)
            return "Not able to catch";
        else return "Able to catch";
    }
}
