package Tasks;

import java.util.Arrays;

public class TheLotteryBothDivs {
    public double find(String[] goodSuffixes) {
        double p10[] = new double[]{0, .1, .01, .001, .0001, .00001, .000001, .0000001, .00000001, .000000001, .0000000001};
        int n = goodSuffixes.length;
        boolean[] take = new boolean[n];
        Arrays.fill(take, true);
        //Arrays.sort(goodSuffixes);
        for (int i = 0; i < n; i++) {
            if (!take[i]) continue;
            for (int j = 0; j < n; j++) {
                if (j != i && goodSuffixes[j].endsWith(goodSuffixes[i])) take[j] = false;
            }
        }
        double cnt = 0;
        for (int i = 0; i < n; i++) {
            if (take[i]) cnt += p10[goodSuffixes[i].length()];
        }
        return cnt;
    }
}
