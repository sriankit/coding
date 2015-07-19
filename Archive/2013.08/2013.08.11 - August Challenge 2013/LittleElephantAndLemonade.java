package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class LittleElephantAndLemonade {
    Queue<Integer> q[];

    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int m = in.readInt();
        int[] p = IOUtils.readIntArray(in, m);
        q = new Queue[n];
        for (int i = 0; i < n; i++) {
            q[i] = new LinkedList<Integer>();
            int c = in.readInt();
            int[] a = IOUtils.readIntArray(in, c);
            Arrays.sort(a);
            for (int i1 = a.length - 1; i1 >= 0; i1--) {
                int ele = a[i1];
                q[i].offer(ele);
            }
        }
        long ans = 0;
        for (int room : p) {
            Queue<Integer> queue = q[room];
            if (queue.size() > 0) ans += queue.poll();
        }
        out.printLine(ans);
    }
}
