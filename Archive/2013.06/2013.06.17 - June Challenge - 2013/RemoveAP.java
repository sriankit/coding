package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.ArrayList;

public class RemoveAP {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        //int[] arr = new int[100000];
        int[] arr = IOUtils.readIntArray(in, n);
        /*for (int i = 0; i < 100000; i++) {
            arr[i] = i + 1;
        } */

        boolean[] del = new boolean[200007];
        ArrayList<Integer> preAns = new ArrayList<Integer>();
        preAns.add(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            if (!del[arr[i]]) {
                for (int num : preAns) {
                    if (arr[i] - (num - arr[i]) >= 0) del[2 * arr[i] - num] = true;
                }
                preAns.add(arr[i]);
            }
        }
        del = new boolean[200007];
        ArrayList<Integer> al = new ArrayList<Integer>();
        for (int i1 : arr) al.add(i1);
        int di = 0;

        do {
            //System.out.println(al.size());
            int d = di / 10 + 1;
            ArrayList<Integer> bl = new ArrayList<Integer>();
            for (int i1 : al) {
                if (!del[i1])
                    bl.add(i1);
            }
            al = bl;
            int a = -1, b = -1, c = -1;
            for (int i = di % 10; i < al.size(); i += d) {
                int num = al.get(i);
                if (a == -1) {
                    a = num;
                    continue;
                }
                if (b == -1) {
                    b = num;
                    continue;
                }
                c = num;
                if (b - a == c - b) {
                    del[c] = true;
                    a = -1;
                    b = -1;
                    continue;
                }
                a = b;
                b = c;
            }
        } while (di-- > 0);
        arr = new int[al.size()];
        //out.printLine(al.size());
        int j = 0;
        for (int num : al) arr[j++] = num;

        ArrayList<Integer> ans = new ArrayList<Integer>();
        ans.add(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            if (!del[arr[i]]) {
                for (int num : ans) {
                    if (arr[i] - (num - arr[i]) >= 0) del[2 * arr[i] - num] = true;
                }
                ans.add(arr[i]);
            }
        }
        if (ans.size() >= preAns.size()) {
            out.printLine(ans.size());
            for (int num : ans) out.print(num + " ");
        } else {
            out.printLine(preAns.size());
            for (int num : preAns) out.print(num + " ");
        }
    }
}
