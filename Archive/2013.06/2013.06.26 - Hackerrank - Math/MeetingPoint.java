package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

public class MeetingPoint {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        double avgx = 0.0;
        double avgy = 0.0;
        long[][] points = new long[n][2];
        for (int i = 0; i < n; i++) {
            long x = in.readLong();
            long y = in.readLong();
            points[i][0] = x;
            points[i][1] = y;
            avgx += x / (double) n;
            avgy += y / (double) n;
        }
        double diff = Math.sqrt((avgx - points[0][0]) * (avgx - points[0][0]) + (avgy - points[0][1]) * (avgy - points[0][1]));
        int ind = 0;
        for (int i = 1; i < n; i++) {
            double ldiff = Math.sqrt((avgx - points[i][0]) * (avgx - points[i][0]) + (avgy - points[i][1]) * (avgy - points[i][1]));
            if (ldiff < diff) {
                ind = i;
                diff = ldiff;
            }
        }
        //System.out.println(ind);
        long ans = 0;
        for (int i = 0; i < n; i++) {
            ans += Math.max(Math.abs(points[i][0] - points[ind][0]), Math.abs(points[i][1] - points[ind][1]));
        }
        out.printLine(ans);
    }
}
