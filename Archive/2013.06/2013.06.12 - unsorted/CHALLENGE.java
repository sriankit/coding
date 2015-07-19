package Learning;

import javaUtils.ArrayUtils;
import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.*;

public class CHALLENGE {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int m = in.readInt();
        int[] arr = IOUtils.readIntArray(in, m);
        /*int[] arr = new int[100005];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        } */
        //algo 1

        ArrayList<Integer> preAns = new ArrayList<Integer>();
        boolean[] delete = new boolean[200007];
        Random random = new Random();
        preAns.add(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            int k = random.nextInt();
            if (k % 4 == 0) continue;
            if (!delete[arr[i]]) {
                for (int num : preAns) {
                    if (arr[i] - (num - arr[i]) >= 0) delete[2 * arr[i] - num] = true;
                }
                preAns.add(arr[i]);
            }
        }
        //algo 2
        int[] del = new int[200005];
        int[] cntdel = new int[200005];
        Arrays.sort(arr);
        ArrayList<Integer> ans = new ArrayList<Integer>();
        ans.add(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            if (cntdel[arr[i]] == 0) {
                for (int num : ans) {
                    if (arr[i] - (num - arr[i]) >= 0) {
                        cntdel[2 * arr[i] - num]++;
                        del[2 * arr[i] - num] = arr[i];
                    }
                }
                ans.add(arr[i]);
            }
        }
        //algo 3
        TreeMap<Integer, ArrayList<Integer>> map = new TreeMap<Integer, ArrayList<Integer>>();
        ArrayList<Integer> reject = new ArrayList<Integer>();
        for (int num : arr) {
            if (cntdel[num] == 1) {
                reject.add(num);
                //System.out.println("num = " + num);
                //System.out.println("rejected by : " + del[num]);
            }
            if (map.get(del[num]) == null) map.put(del[num], new ArrayList<Integer>());
            map.get(del[num]).add(num);
        }
        ArrayList<Integer> finalAns = new ArrayList<Integer>();

        for (int num : ans) {
            ArrayList<Integer> temp = map.get(num);
            if (temp != null) {
                int sz = temp.size();
                if (sz > 1) {
                    finalAns.addAll(temp);
                } else finalAns.add(num);
            } else finalAns.add(num);
        }
        //correct the final answer
        //System.out.println(finalAns);
        Collections.sort(finalAns);
        ArrayList<Integer> ultimateAns = new ArrayList<Integer>();
        Iterator it = finalAns.iterator();
        ultimateAns.add((Integer) it.next());
        boolean[] taken = new boolean[200005];
        while (it.hasNext()) {
            int a = (Integer) it.next();
            if (!taken[a]) {
                //System.out.println("got " + a);
                ultimateAns.add(a);
                for (int num : ultimateAns) {
                    if (a - (num - a) >= 0) taken[2 * a - num] = true;
                }
            }
        }
        //algo 4
        delete = new boolean[200005];
        finalAns = new ArrayList<Integer>();
        //System.out.println("ans = " + ans);
        for (int num : ans) {
            ArrayList<Integer> temp = map.get(num);
            if (temp != null) {
                int sz = temp.size();
                if (sz > 1 || delete[num]) {
                    //System.out.println("for " + num + "temp = " + temp);
                    for (int number : temp) {
                        if (delete[number]) continue;
                        finalAns.add(number);
                        for (int numx : finalAns)
                            if (number - (numx - number) >= 0) delete[2 * number - numx] = true;
                    }
                } else if (!delete[num]) {
                    finalAns.add(num);
                    for (int numx : finalAns)
                        if (num - (numx - num) >= 0) delete[2 * num - numx] = true;
                }
            } else if (!delete[num]) {
                finalAns.add(num);
                for (int numx : finalAns)
                    if (num - (numx - num) >= 0) delete[2 * num - numx] = true;
            }
        }
        //System.out.println(finalAns);
        int ind = ArrayUtils.maxPosition(new int[]{preAns.size(), ultimateAns.size(), ans.size(), finalAns.size()});
        switch (ind) {
            case 0:
                out.printLine(preAns.size());
                for (int num : preAns) out.print(num + " ");
                break;
            case 1:
                //if(ultimateAns.size() > ans.size()){
                out.printLine(ultimateAns.size());
                for (int num : ultimateAns) out.print(num + " ");
                break;
            case 2:
                out.printLine(ans.size());
                for (int num : ans) out.print(num + " ");
                break;
            case 3:
                out.printLine(finalAns.size());
                for (int num : finalAns) out.print(num + " ");
                break;
        }
    }
}
