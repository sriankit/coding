package Tasks;

import javaUtils.BIT;
import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.TreeMap;

public class LittleElephantAndBubbleSort {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int d = in.readInt();
        int[] a = IOUtils.readIntArray(in, n);
        int[] p = IOUtils.readIntArray(in, n);
        int[] b = new int[n];
        for (int i = 0; i < n; i++) {
            b[i] = a[i] + d;
        }
        TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
        for (int i = 0; i < n; i++) {
            map.put(a[i], 1);
            map.put(b[i], 1);
        }
        int cnt = 2;
        //System.out.println(map.keySet());
        for (Integer next : map.keySet()) {
            map.put(next, cnt++);
        }
        for (int i = 0; i < n; i++) {
            a[i] = map.get(a[i]);
            b[i] = map.get(b[i]);
            /*System.out.println("a[i] = " + a[i]);
            System.out.println("b[i] = " + b[i]);*/
        }
        BIT tree = new BIT(cnt);
        tree.update(a[n - 1], 100 - p[n - 1]);
        tree.update(b[n - 1], p[n - 1]);
        long ans = 0;
        for (int i = n - 2; i >= 0; i--) {
            ans += (100 - p[i]) * tree.freqTo(a[i] - 1);
            ans += p[i] * tree.freqTo(b[i] - 1);
            tree.update(a[i], 100 - p[i]);
            tree.update(b[i], p[i]);
        }
        out.printf("%.04f\n", ans / 10000.0);
    }
}
