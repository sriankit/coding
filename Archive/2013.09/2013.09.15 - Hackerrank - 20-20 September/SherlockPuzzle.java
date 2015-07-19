package Tasks;

import javaUtils.InReader;
import javaUtils.MinBIT;
import javaUtils.OutputWriter;

import java.util.HashMap;

public class SherlockPuzzle {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int k = in.readInt();
        String s = in.readString();
        int n = s.length();


        int precnt0[] = new int[n + 1];
        int postcnt0[] = new int[n + 1];
        int precnt1[] = new int[n + 1];
        int postcnt1[] = new int[n + 1];
        precnt0[0] = 0;
        precnt1[0] = 0;
        postcnt0[n] = 0;
        postcnt1[n] = 0;
        int tot0 = 0, tot1 = 0;
        for (int i = 1; i <= n; i++) {
            precnt1[i] = precnt1[i - 1];
            precnt0[i] = precnt0[i - 1];
            if (s.charAt(i - 1) == '1') {
                precnt1[i] = precnt1[i - 1] + 1;
                tot1++;
            } else {
                precnt0[i] = precnt0[i - 1] + 1;
                tot0++;
            }
        }
        for (int i = n - 1; i >= 0; i--) {
            postcnt1[i] = postcnt1[i + 1];
            postcnt0[i] = postcnt0[i + 1];
            if (s.charAt(i) == '1') postcnt1[i] = postcnt1[i + 1] + 1;
            else postcnt0[i] = postcnt0[i + 1] + 1;
        }

        int[] postdiff = new int[n + 1];
        int[] prediff = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            postdiff[i] = 2 * postcnt0[i] - 3 * postcnt1[i];
            prediff[i] = 2 * precnt0[i] - 3 * precnt1[i];
        }

        if (2 * tot0 <= 3 * tot1) out.printLine(k * (long) n);
        else {

            if (k == 1) {

                int newSum = 0;
                int min = 0;
                for (int i = 0; i < n; i++) {
                    if (i < n) min = Math.min(3 * precnt1[i] - 2 * precnt0[i], min);
                }

                //NOrmalizing the sum using min

                MinBIT tree = new MinBIT(3 * 2 * n - min + 1);
                HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
                map.put(3 * precnt1[1] - 2 * precnt0[1] - min + 1, 1);
                tree.update(3 * precnt1[1] - 2 * precnt0[1] - min + 1, 1);

                long ans = 0;
                //System.out.println("min = " + min);
                int sm = 3 * precnt1[1] - 2 * precnt0[1] - min + 1;
                for (int i = 1; i < n; i++) {
                    sm = 3 * precnt1[i + 1] - 2 * precnt0[i + 1] - min + 1;
                    long j = tree.freqTo(sm - newSum);
                    //System.out.println("going for  " + (sm - newSum) );
                    if (!map.containsKey(sm)) {
                        tree.update(sm, i + 1);
                        map.put(sm, 1);
                    }
                    if (j != Integer.MAX_VALUE) {
                        //System.out.println("j = " + j);
                        ans = Math.max(i + 1 - j, ans);
                        //System.out.println("ans = " + ans);
                    }
                }
                out.printLine(ans);
                return;

            }

            //Twisted impl of Kadane's
            int max = 0;
            int sum = 0;
            for (int i = 0; i < 2 * n; i++) {
                if (s.charAt(i % n) == '1') sum += 3;
                else sum -= 2;
                if (sum < 0) sum = 0;
                max = Math.max(sum, max);
            }
            if (max == 0) {
                out.printLine(0);
                return;
            }

            //System.out.println("max = " + max);

            int oneDiff = 3 * tot1 - 2 * tot0;
            int cnt = Math.min(max / -oneDiff, k - 2);
            // now find max length for keeping this cnt;

            int newSum = cnt * -oneDiff;
            int min = 0;
            for (int i = 0; i < 2 * n; i++) {
                if (i < n) min = Math.min(3 * precnt1[i] - 2 * precnt0[i], min);
                else min = Math.min(3 * precnt1[i - n] - 2 * precnt0[i - n] + oneDiff, min);
            }

            //NOrmalizing the sum using min

            MinBIT tree = new MinBIT(3 * 2 * n - min + 1);
            HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
            map.put(3 * precnt1[1] - 2 * precnt0[1] - min + 1, 1);
            tree.update(3 * precnt1[1] - 2 * precnt0[1] - min + 1, 1);

            long ans = 0;
            //System.out.println("min = " + min);
            int sm = 3 * precnt1[1] - 2 * precnt0[1] - min + 1;
            for (int i = 1; i < 2 * n; i++) {
                sm = 3 * precnt1[i % n + 1] - 2 * precnt0[i % n + 1] - min + 1 + (i < n ? 0 : oneDiff);
                long j = tree.freqTo(sm - newSum);
                //System.out.println("going for  " + (sm - newSum) );
                if (!map.containsKey(sm)) {
                    tree.update(sm, i + 1);
                    map.put(sm, 1);
                }
                if (j != Integer.MAX_VALUE) {
                    //System.out.println("j = " + j);
                    ans = Math.max(i + 1 - j, ans);
                    //System.out.println("ans = " + ans);
                }
            }

            out.printLine(ans + cnt * n);
        }


    }
}
