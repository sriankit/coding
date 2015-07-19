package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.Arrays;
import java.util.Comparator;

public class TaskD {
    private void dp(int i, int k) {

    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int m = in.readInt();
        int k = in.readInt();
        comp[] arr = new comp[m];
        int minl = 301, maxr = 0;
        for (int i = 0; i < m; i++) {
            int l = in.readInt();
            int r = in.readInt();
            int c = in.readInt();
            minl = Math.min(minl, l);
            maxr = Math.max(maxr, r);
            arr[i] = new comp(l, r, c);
        }

        Arrays.sort(arr, new Comparator<comp>() {
            @Override
            public int compare(comp o1, comp o2) {
                int diff = o1.l - o2.l;
                if (diff == 0) return o1.r - o2.r;
                else return diff;
            }
        });


    }
}

class comp {
    int l, r, c;

    comp(int c, int l, int r) {
        this.c = c;
        this.l = l;
        this.r = r;
    }
}
