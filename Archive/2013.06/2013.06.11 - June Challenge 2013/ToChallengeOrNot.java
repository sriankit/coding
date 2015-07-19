package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.*;

public class ToChallengeOrNot {
    int[] arr;

    ArrayList<Integer> randComp() {
        ArrayList<Integer> preAns = new ArrayList<Integer>();
        boolean[] delete = new boolean[200007];
        Random random = new Random();
        preAns.add(arr[0]);
        int f = 1, b = arr.length - 1;
        for (int i = 1; i < arr.length; i++) {
            int ind;
            if (i % 2 == 0) ind = b--;
            else ind = f++;
            int k = random.nextInt();
            if (k % 4 == 0) continue;
            if (!delete[arr[ind]]) {
                for (int num : preAns) {
                    if (arr[ind] - (num - arr[ind]) >= 0) delete[2 * arr[ind] - num] = true;
                }
                preAns.add(arr[ind]);
            }
        }
        //System.out.println(preAns);
        preAns.clear();
        for (int num : arr) if (!delete[num]) preAns.add(num);
        return preAns;
    }

    ArrayList<Integer> preComp() {
        ArrayList<Integer> preAns = new ArrayList<Integer>();
        boolean[] delete = new boolean[200007];
        Random random = new Random();
        preAns.add(arr[0]);
        int f = 1, b = arr.length - 1;
        for (int i = 1; i < arr.length; i++) {
            int ind;
            if (i % 2 == 0) ind = b--;
            else ind = f++;
            int k = random.nextInt();
            //if(k % 4 == 0) continue;
            if (!delete[arr[ind]]) {
                for (int num : preAns) {
                    if (arr[ind] - (num - arr[ind]) >= 0) delete[2 * arr[ind] - num] = true;

                }
                preAns.add(arr[ind]);
            }
        }
        //System.out.println(preAns);
        preAns.clear();
        for (int num : arr) if (!delete[num]) preAns.add(num);
        return preAns;
    }

    ArrayList<Integer> ultiComp() {
        int[] del = new int[200005];
        int[] cntdel = new int[200005];
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

        TreeMap<Integer, ArrayList<Integer>> map = new TreeMap<Integer, ArrayList<Integer>>();
        ArrayList<Integer> reject = new ArrayList<Integer>();
        for (int num : arr) {
            if (cntdel[num] == 1) {
                reject.add(num);
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
        return ultimateAns;
    }

    ArrayList<Integer> finalComp() {
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
        //ith element to be removed from sol
        boolean[] delete = new boolean[200005];
        for (int rem : ans) {
            ArrayList<Integer> temp = map.get(rem);
            if (temp == null) {
                if (!delete[rem]) {
                    finalAns.add(rem);
                    for (int num : finalAns) {
                        if (rem - (num - rem) >= 0) delete[2 * rem - num] = true;
                    }
                }
            } else {
                int sz = 0;
                for (int checkNo : temp) if (!delete[checkNo]) sz++;
                if (delete[rem] || sz > 1) {
                    for (int chk : temp) {
                        if (!delete[chk]) {
                            finalAns.add(chk);
                            for (int num : finalAns) {
                                if (chk - (num - chk) >= 0) delete[2 * chk - num] = true;
                            }
                        }
                    }
                } else {
                    if (!delete[rem]) {
                        finalAns.add(rem);
                        for (int num : finalAns) {
                            if (rem - (num - rem) >= 0) delete[2 * rem - num] = true;
                        }
                    }
                }
            }
        }
        ArrayList<Integer> al = new ArrayList<Integer>();
        for (int num : finalAns) if (!delete[num]) al.add(num);
        return al;
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        arr = IOUtils.readIntArray(in, n);
        ArrayList<Integer> ans[] = new ArrayList[4];
        Arrays.sort(arr);

        ans[0] = randComp();
        ans[1] = preComp();
        ans[2] = ultiComp();
        ans[3] = finalComp();

        int chose = 0;

        for (int i = 1; i < 4; i++) {
            if (ans[chose].size() < ans[i].size()) chose = i;
        }

        out.printLine(ans[chose].size());
        for (int num : ans[chose]) out.print(num + " ");
    }
}
