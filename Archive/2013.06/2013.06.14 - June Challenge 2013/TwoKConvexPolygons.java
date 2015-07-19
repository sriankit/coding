package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;


public class TwoKConvexPolygons {

    final Node SENTINEL = new Node("", -1);
    IndRetainer[] arr;
    long[] preSum;
    long startTime;
    boolean check = false;

    void process(int st, int end, ArrayList<Node>[] a) {
        //1s in mask are going to be taken
        int lim = 1 << (end - st + 1);
        //System.out.println("lim = " + lim);
        for (int mask = 0; mask < lim; mask++) {
            Node node = new Node();
            int k = 0;
            int rmask = mask;
            for (int i = 0; end - i >= st; i++, rmask >>= 1) {
                if ((rmask & 1) == 1) {
                    node.s1 += arr[end - i].val;
                    node.mask = '1' + node.mask;
                    k++;
                } else {
                    node.mask = '0' + node.mask;
                }
            }
            assert (rmask == 0);
            if (a[k] == null) a[k] = new ArrayList<Node>();
            a[k].add(node);
        }
        //sort respective buckets
        //System.out.println("Going to Sort");
        for (int i = 0; i < a.length; i++) {
            if (a[i] == null) continue;
            Collections.sort(a[i]);
        }
    }

    Node find_ans(ArrayList<Node> pre, ArrayList<Node> suf, long s1, long total, long s2) {
        //applying two pointer to find ans
        if (pre == null || suf == null) return SENTINEL;
        /*System.out.println("Prefix : ");
        for (Node NODE : pre) System.out.println("**  NODE = " + NODE);
        System.out.println("Suffix : ");
        for (Node NODE : pre) System.out.println("**  NODE = " + NODE);   */

        int p1 = 0, p2 = suf.size() - 1;
        Node node = new Node();

        while (p1 < pre.size() && p2 >= 0) {
            node.s1 = pre.get(p1).s1 + suf.get(p2).s1;
            node.mask = pre.get(p1).mask + suf.get(p2).mask;
            if (node.s1 <= s1) {
                p1++;
                continue;
            }
            if (node.s1 > s1 && total - node.s1 > s2) return node.copy();
            else p2--;
        }
        return SENTINEL;
    }

    long getpreSum(int i) {
        if (i >= 0) return preSum[i];
        else return 0;
    }

    int find(int st, int k) {
        for (int i = st + k - 1; i < arr.length; i++) {
            if (getpreSum(i - 1) - getpreSum(i - k) > arr[i].val) return i;
        }
        return -1;
    }

    boolean check(int k, OutputWriter out) {
        int i1 = find(0, k);
        if (i1 == -1) {
            out.printLine("No");
            return true;
        }
        int i2 = find(i1 + 1, k);
        if (i2 == -1) return false;
        out.printLine("Yes");
        for (int i = i1 - k + 1; i <= i1; i++) {
            out.print(arr[i].ind + " ");
        }
        for (int i = i2 - k + 1; i <= i2; i++) {
            out.print(arr[i].ind + " ");
        }
        return true;

    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int k = in.readInt();
        startTime = System.currentTimeMillis();
        preSum = new long[n];

        int[] ar = IOUtils.readIntArray(in, n);
        arr = new IndRetainer[ar.length];
        for (int i = 0; i < n; i++) {
            arr[i] = new IndRetainer();
            arr[i].val = ar[i];
            arr[i].ind = i + 1;
        }
        Arrays.sort(arr, new Comparator<IndRetainer>() {
            @Override
            public int compare(IndRetainer o1, IndRetainer o2) {
                return o1.val - o2.val;
            }
        });
        System.out.println(Arrays.toString(arr));
        ///check = true;

        for (int i = 0; i < n; i++) {
            if (i > 0) preSum[i] = preSum[i - 1] + arr[i].val;
            else preSum[0] = arr[0].val;
        }

        if (check(k, out)) {
            if (check) while (System.currentTimeMillis() - startTime < 3000 - 10) ;
            return;
        }

        //loooping for maximums of two answers

        for (int max1 = k - 1; max1 < n; max1++) {
            for (int max2 = max1 + 1; max2 <= max1 + k && max2 < n; max2++) {
                long sum1 = arr[max1].val;
                long sum2 = arr[max2].val - (preSum[max2 - 1] - preSum[max1]); //all numbers betwen max1 and max2 to be taken but rem that sum has to be greater than only arr[max2]
                int k1 = k - 1;
                int k2 = k - (max2 - max1);
                if (k1 + k2 > max1) continue; //not enf numbers in prefix
                int e = max1 - 1;

                int s = e - (k1 + k2) + 1;   //inclusive ranges used
                //System.out.println("**trying   s = " + s + "  " + "e = " + e + " for " + "max1 = " + max1 + "  " + "max2 = " + max2);
                long total = getpreSum(e) - getpreSum(s - 1);
                //checking if any soln may be possible

                if ((k1 > 0 && getpreSum(e) - getpreSum(e - k1) <= sum1) || (k2 > 0 && getpreSum(e) - getpreSum(e - k2) <= sum2))
                    continue;
                //range is ready
                //if(k1 == 0) sum1 = 0;
                //if(k2 == 0) sum2 = -1;
                //System.out.println("s = " + s + "  " + "e = " + e);
                //System.out.println("sum2 = " + sum2);
                ArrayList<Node>[] pre = new ArrayList[10];
                ArrayList<Node>[] suf = new ArrayList[10];
                int cnt = k1 + k2;
                int mid = s + e >> 1;

                //carry out meet-in-the-middle attack
                process(s, mid, pre);
                //System.out.println("Processed prefix");
                process(mid + 1, e, suf);
                //System.out.println("Done Processing");
                Node ans = new Node();
                for (int j = 0; j <= k1; j++) {
                    //considering that j elements are picked in pre
                    ans = find_ans(pre[j], suf[k1 - j], sum1, total, sum2);
                    if (ans.s1 != -1) break;
                }
                if (ans.s1 != -1) {
                    //System.out.println("s = " + s + "  " + "e = " + e);
                    out.printLine("Yes");
                    for (int i = 0; i < cnt; i++) {
                        if (ans.mask.charAt(i) == '1') {
                            out.print(arr[s + i].ind);
                            out.print(' ');
                        }
                    }
                    out.print(max1 + 1);
                    out.print(' ');
                    for (int i = 0; i < cnt; i++) {
                        if (ans.mask.charAt(i) == '0') {
                            out.print(arr[s + i].ind);
                            out.print(' ');
                        }
                    }
                    for (int i = max1 + 1; i <= max2; i++) {
                        out.print(arr[i].ind);
                        out.print(' ');
                    }
                    if (check) while ((System.currentTimeMillis() - startTime) < (3000 - .01)) ;
                    return;
                }
            }
        }
        if (check) while (System.currentTimeMillis() - startTime < 3000 - 10) ;
        out.printLine("No");
    }

    class Node implements Comparable<Node> {
        String mask;
        long s1;

        Node() {
            mask = "";
            s1 = 0;
        }

        Node(String mask, long s1) {
            this.mask = mask;
            this.s1 = s1;
        }

        @Override
        public int compareTo(Node o) {
            if (o.s1 == this.s1) return 0;
            return o.s1 > this.s1 ? -1 : 1;
        }

        Node copy() {
            Node node = new Node();
            node.s1 = this.s1;
            node.mask = this.mask;
            return node;
        }

        @Override
        public String toString() {
            return "[ " + "\"\"" + mask + "\"\"" + "  " + "sum = " + s1 + " ] ";
        }
    }

    class IndRetainer {
        int val, ind;

        @Override
        public String toString() {
            return "[ " + ind + "  " + val + "  ]";
        }
    }
}


