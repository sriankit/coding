package Tasks;

import java.util.Arrays;
import java.util.Comparator;

public class CandidatesSelectionEasy {
    public int[] sort(final String[] score, final int x) {
        int n = score.length;
        Integer[] ord = new Integer[n];
        for (int i = 0; i < n; i++) {
            ord[i] = i;
        }
        Arrays.sort(ord, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return score[o1].charAt(x) - score[o2].charAt(x);
            }
        });
        int[] ret = new int[n];
        for (int i = 0; i < n; i++) {
            ret[i] = (int) ord[i];
        }
        return ret;

    }
}
