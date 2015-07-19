package Tasks;

import javaUtils.BIT;
import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

public class SEABAL_revamped {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int m = in.readInt();
        int[] arr = IOUtils.readIntArray(in, n);
        BIT front = new BIT(n + 3);
        //BIT rear = new BIT(n + 3);
        for (int i = 0; i < n; i++) {
            front.update(i + 2, arr[i]);
        }
        int last = 0;
        Data[] pairs = new Data[m];
        for (int i = 0; i < m; i++) {
            int l = in.readInt() - 1;
            int r = in.readInt() - 1;
            pairs[i] = new Data(l, r);
            long sum = front.freqTo(r + 2) - front.freqTo(l - 1 + 2);
            if (sum == 0) last++;
        }
        SegTree tree = new SegTree(pairs, n);

        int k = in.readInt();
        while (k-- > 0) {
            //System.out.println("last = " + last);
            int ind = in.readInt() + last - 1;
            int ans;
            arr[ind]--;
            front.update(ind + 2, -1);
            if (arr[ind] > 0) {
                ans = 0;
            } else {
                //for left extent
                int lt = ind;
                int lo = 0, hi = ind;
                long sum = front.freqTo(hi + 2);
                while (lo <= hi) {
                    int mid = lo + hi >> 1;
                    long val = front.freqTo(mid + 2);
                    if (val == sum) {
                        lt = mid;
                        hi = mid - 1;
                    } else {
                        lo = mid + 1;
                    }
                }
                int rt = ind;
                lo = ind;
                hi = n - 1;

                while (lo <= hi) {
                    int mid = lo + hi >> 1;
                    long val = front.freqTo(mid + 2);
                    if (val == sum) {
                        rt = mid;
                        lo = mid + 1;
                    } else {
                        hi = mid - 1;
                    }
                }
                if (arr[lt] != 0) lt++;
                ans = tree.query(lt, rt) - tree.query(lt, ind - 1) - tree.query(ind + 1, rt);
            }
            out.printLine(ans + last);
            last += ans;
            //System.out.println("last = " + last);
        }
        /*while(k --> 0) {
            int l = in.readInt() - 1;
            int r = in.readInt() - 1;
            out.printLine(tree.query(l, r));
        } */
    }


}
