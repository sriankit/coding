package Tasks;

import javaUtils.BIT;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;

public class cmi1 {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        BIT tree = new BIT(200005);
        int arr[] = new int[2 * n];
        TreeMap<Integer, Integer> tm = new TreeMap<Integer, Integer>();
        int j = 0;
        for (int i = 0; i < n; i++) {
            int s, e;
            s = in.readInt();
            arr[j++] = s;
            tm.put(s, 1);
            e = in.readInt();
            arr[j++] = e;
            tm.put(e, 1);
        }
        //truncation
        int cntr = 1;
        for (int k : tm.keySet())
            tm.put(k, cntr++);
        //System.out.println(tm.values());
        int[] sol = new int[n];
        HashMap<Integer, Integer> labels = new HashMap<Integer, Integer>();
        int ans = 0;
        TreeSet<Integer>[] implicitArray = new TreeSet[200005];
        for (int i = 0; i < 200005; i++) {
            implicitArray[i] = new TreeSet<Integer>();
        }
        for (int i = 0; i < j; i += 2) {
            int s = tm.get(arr[i]), e = tm.get(arr[i + 1]);
            int idx = s - 1;
            int r = tree.minFreqTo(idx);
            if (r != Integer.MAX_VALUE) {
                int end = labels.get(r);
                sol[i / 2] = implicitArray[end].pollFirst();
                if (implicitArray[end].size() > 0) {
                    tree.update(end, implicitArray[end].first());
                } else tree.update(end, 0);
                implicitArray[e].add(r);
                labels.put(r, e);
                tree.update(e, implicitArray[e].first());
            } else {
                ans++;
                labels.put(ans, e);
                implicitArray[e].add(ans);
                sol[i / 2] = ans;
                tree.update(e, implicitArray[e].first());
            }
        }
        out.printLine(ans);
        for (int i = 0; i < n; i++) {
            //out.printf("%d ", sol[i]);
            out.print(sol[i]);
            out.print(' ');
        }
    }
}