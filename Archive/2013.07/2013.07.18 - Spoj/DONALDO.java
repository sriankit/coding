package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class DONALDO {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int[] tyms = new int[n];
        for (int i = 0; i < n; i++) {
            String s = in.readString();
            int m = 3600;
            for (StringTokenizer tokenizer = new StringTokenizer(s, ":"); tokenizer.hasMoreTokens(); ) {
                String s1 = tokenizer.nextToken();
                tyms[i] += Integer.parseInt(s1) * m;
                m /= 60;
            }
        }
        int dur = in.readInt();
        int ans = 0;
        Arrays.sort(tyms);
        //System.out.println(Arrays.toString(tyms));
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
        for (int i = 0; i < n; i++) {
            if (pq.size() > 0) {
                long top = pq.peek();
                if (tyms[i] - top >= dur) {
                    pq.poll();
                    pq.add(tyms[i]);
                } else {
                    pq.add(tyms[i]);
                    ans++;
                }
            } else {
                ans++;
                pq.add(tyms[i]);
            }

        }
        out.printLine("Case " + testNumber + ": " + ans);
    }
}
