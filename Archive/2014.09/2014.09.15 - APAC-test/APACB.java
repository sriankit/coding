package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

public class APACB {
    double store[][];
    void go(int l, int target) {
        if(l > target) return;
        int j = 1;
        for (int i = 1; i <= l; i++) {
            for (; j <= (i * (i + 1) >> 1); j++) {
                if (store[l][j] > 250.0) {
                    double excess = store[l][i] - 250;
                    store[l + 1][j] += excess / 3;
                    store[l + 1][j + i] += excess / 3;
                    store[l + 1][j + i + 1] += excess / 3;
                    store[l][i] = 250.0;
                }
            }
        }
        go(l + 1, target);
    }
    public void solve(int testNumber, final InReader in, final OutputWriter out) {
        Thread t = new Thread(null, () -> solveIt(testNumber, in, out),"oh yeah",1<<28);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void solveIt(int testNumber, InReader in, OutputWriter out) {
        int b = in.readInt();
        int l = in.readInt();
        int n = in.readInt();
        int mx = l + 2;
        store = new double[mx + 1][mx * (mx + 1) >> 1];
        store[1][1] = b * 750;
        go(1, l);
        out.printLine(store[l][n]);
    }
 }
