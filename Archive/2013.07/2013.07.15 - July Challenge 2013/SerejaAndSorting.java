package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class SerejaAndSorting {
    int k;
    int[] arr;
    long TL = 5500;
    long start;

    ArrayList<Integer> getBest(int[] arr, boolean svn) {
        ArrayList<ArrayList<Integer>> answers = new ArrayList<ArrayList<Integer>>();
        answers.add(algo1(arr));
        answers.add(algo2(arr));
        answers.add(algo3(arr));
        answers.add(algo4(arr));
        answers.add(algo5(arr));
        if (svn) {
            answers.add(algo7(arr));
            answers.add(algo8(arr));
            answers.add(algo9(arr));
            answers.add(algo10(arr));
            answers.add(algo11(arr));
        }
        answers.add(algo6(arr));
        ArrayList<Integer> ret = answers.get(0);

        for (ArrayList<Integer> ele : answers) {
            if (ele.size() < ret.size()) ret = ele;
        }
        return ret;
    }

    boolean isAlreadySorted(int from, int to) {
        for (int i = from + 1; i < to; i++) {
            if (arr[i] < arr[i - 1]) return false;
        }
        return true;
    }

    ArrayList<Integer> solveSub(int from, int to) {
        int n = to - from;
        ArrayList<Integer> ret = getBest(Arrays.copyOfRange(arr, from, to), true);
        for (int i = 0; i < ret.size(); i++) {
            ret.set(i, ret.get(i) + from);
        }
        if (TL - (System.currentTimeMillis() - start) < 1195) return ret;
        if (n <= Math.max(arr.length / 4, 2) * k) {
            //System.out.println("returned " + ret + " for " + from + "  " + to + " " + Arrays.toString(Arrays.copyOfRange(arr, from, to)));
            Arrays.sort(arr, from, to);
            return ret;
        }
        int mid = from + to >> 1;
        int[] sorted = Arrays.copyOfRange(arr, from, to).clone();
        Arrays.sort(sorted);
        int[] masks = new int[arr.length];
        HashMap<Integer, Integer> cnt = new HashMap<Integer, Integer>();
        for (int i = n / 2; i < n; i++) {
            if (cnt.containsKey(sorted[i])) cnt.put(sorted[i], cnt.get(sorted[i]) + 1);
            else cnt.put(sorted[i], 1);
        }
        for (int i = to - 1; i >= from; i--) {
            if (cnt.containsKey(arr[i]) && cnt.get(arr[i]) > 0) {
                masks[i] = 1;
                cnt.put(arr[i], cnt.get(arr[i]) - 1);
            }
        }
        //System.out.println(Arrays.toString(masks));
        //System.out.println(Arrays.toString(sorted));
        ArrayList<Integer> ops = new ArrayList<Integer>();
        for (int j = from; j < to; j++) {
            if (masks[j] == 1) {
                boolean ok = false;
                int end = Math.min(j + k, to);
                for (int i = j; i < end; i++) {
                    if (masks[i] == 0) {
                        ok = true;
                        break;
                    }
                }
                if (ok) {
                    int st = Math.max(end - k, 0);
                    ops.add(st + 1);
                    Arrays.sort(arr, st, end);
                    Arrays.sort(masks, st, end);
                } else break;
            }
        }
        //System.out.println(Arrays.toString(masks));
        int done = ops.size();
        ArrayList<Integer> posible = (ArrayList<Integer>) ops.clone();
        posible.addAll(getBest(Arrays.copyOfRange(arr, from, to), true));
        for (int i = done; i < posible.size(); i++) {
            posible.set(i, posible.get(i) + from);
        }
        for (int i = to - 1; i > from; i--) {
            if (masks[i] == 1) continue;
            //find 1 by going in reverse
            int ind1 = -1;
            for (int j = i - 1; j >= from; j--) {
                if (masks[j] == 1) {
                    ind1 = j;
                    break;
                }
            }
            if (ind1 == -1) break;
            else {
                int j = ind1;
                j += ((i - j) % (k - 1) == 0 ? k - 1 : (i - j) % (k - 1));
                //System.out.println("j = " + j);
                while (j <= i) {
                    int st = Math.max(j - k + 1, 0);
                    //System.out.println("st = " + st);
                    ops.add(st + 1);
                    Arrays.sort(arr, st, Math.min(st + k, to));
                    Arrays.sort(masks, st, Math.min(st + k, to));
                    j += k - 1;
                    //System.out.println(Arrays.toString(masks));
                }
            }
        }

        //System.out.println("Arrays.toString(arr) = " + Arrays.toString(arr));
        ops.addAll(solveSub(from, mid));
        ops.addAll(solveSub(mid, to));
        //System.out.println("returned " + ops + " for " + from + "  " + to);
        if (ops.size() < ret.size() && ops.size() < posible.size()) return ops;
        else if (ret.size() < posible.size()) return ret;
        else return posible;
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        testNumber = in.readInt();
        start = System.currentTimeMillis();
        while (testNumber-- > 0) {
            int n = in.readInt();
            k = in.readInt();
            arr = IOUtils.readIntArray(in, n);
            ArrayList<Integer> ans = new ArrayList<Integer>();
            ans = solveSub(0, arr.length);
            //System.out.println("{ " + Arrays.toString(arr));
            //System.out.println(ans);
            out.printLine(ans.size());
            for (int ele : ans) out.print(ele + " ");
            out.printLine("");
            //System.out.println(testNumber);
        }
    }

    ArrayList<Integer> algo1(int[] inp) {
        int[] arr = new int[inp.length];
        System.arraycopy(inp, 0, arr, 0, inp.length);
        int n = inp.length;

        ArrayList<Integer> ops = new ArrayList<Integer>();
        boolean ok = true;
        do {
            ok = true;
            for (int i = n - 2; i >= 0; --i) {
                if (arr[i] > arr[i + 1]) {
                    int st = Math.max(i + 1 - k + 1, 0);
                    ops.add(st + 1);
                    Arrays.sort(arr, st, Math.min(st + k, n));
                    ok = false;
                }
            }
        } while (!ok);
        return ops;
    }

    ArrayList<Integer> algo2(int[] inp) {
        int[] arr = new int[inp.length];
        System.arraycopy(inp, 0, arr, 0, inp.length);
        int n = inp.length;

        ArrayList<Integer> ops = new ArrayList<Integer>();
        boolean ok = true;
        do {
            ok = true;
            for (int i = 1; i < n; i++) {
                if (arr[i] < arr[i - 1]) {
                    int st = i - 1;
                    ops.add(st + 1);
                    Arrays.sort(arr, st, Math.min(st + k, n));
                    ok = false;
                }
            }
        } while (!ok);
        return ops;
    }

    ArrayList<Integer> algo3(int[] inp) {
        int[] arr = new int[inp.length];
        System.arraycopy(inp, 0, arr, 0, inp.length);
        int n = inp.length;

        ArrayList<Integer> ops = new ArrayList<Integer>();
        int[] sorted = new int[n];
        System.arraycopy(inp, 0, sorted, 0, n);
        Arrays.sort(sorted);

        for (int i = 0; i < n; i++) {
            if (sorted[i] == arr[i]) continue;
            int j;
            for (j = i + 1; j < n; j++) {
                if (arr[j] == sorted[i]) break;
            }
            j -= ((j - i) % (k - 1) == 0 ? k - 1 : (j - i) % (k - 1));
            while (j >= i) {
                ops.add(j + 1);
                Arrays.sort(arr, j, Math.min(j + k, n));
                j -= k - 1;
            }
        }
        return ops;
    }

    ArrayList<Integer> algo4(int[] inp) {
        int[] arr = new int[inp.length];
        System.arraycopy(inp, 0, arr, 0, inp.length);
        int n = inp.length;

        ArrayList<Integer> ops = new ArrayList<Integer>();
        int[] sorted = new int[n];
        System.arraycopy(inp, 0, sorted, 0, n);
        Arrays.sort(sorted);

        for (int i = 0; i < n; i++) {
            if (sorted[i] == arr[i]) continue;
            int j;
            for (j = n - 1; j > i; j--) {
                if (arr[j] == sorted[i]) break;
            }
            j -= ((j - i) % (k - 1) == 0 ? k - 1 : (j - i) % (k - 1));
            while (j >= i) {
                ops.add(j + 1);
                Arrays.sort(arr, j, Math.min(j + k, n));
                j -= k - 1;
            }
        }
        return ops;
    }

    ArrayList<Integer> algo5(int[] inp) {
        int[] arr = new int[inp.length];
        System.arraycopy(inp, 0, arr, 0, inp.length);
        int n = inp.length;

        ArrayList<Integer> ops = new ArrayList<Integer>();
        int[] sorted = new int[n];
        System.arraycopy(inp, 0, sorted, 0, n);
        Arrays.sort(sorted);

        for (int i = n - 1; i >= 0; i--) {
            if (sorted[i] == arr[i]) continue;
            int j;
            for (j = 0; j < i; j++) {
                if (arr[j] == sorted[i]) break;
            }
            j += ((i - j) % (k - 1) == 0 ? k - 1 : (i - j) % (k - 1));
            //System.out.println("j = " + j);
            while (j <= i) {
                int st = Math.max(j - k + 1, 0);
                //System.out.println("st = " + st);
                ops.add(st + 1);
                Arrays.sort(arr, st, Math.min(st + k, n));
                j += k - 1;
            }
        }
        //System.out.println(ops);
        return ops;
    }

    ArrayList<Integer> algo6(int[] inp) {
        int[] arr = new int[inp.length];
        System.arraycopy(inp, 0, arr, 0, inp.length);
        int n = inp.length;

        ArrayList<Integer> ops = new ArrayList<Integer>();
        int[] sorted = new int[n];
        System.arraycopy(inp, 0, sorted, 0, n);
        Arrays.sort(sorted);

        for (int i = n - 1; i >= 0; i--) {
            if (sorted[i] == arr[i]) continue;
            int j;
            for (j = i - 1; j >= 0; j--) {
                if (arr[j] == sorted[i]) break;
            }
            j += ((i - j) % (k - 1) == 0 ? k - 1 : (i - j) % (k - 1));
            //System.out.println("j = " + j);
            while (j <= i) {
                int st = Math.max(j - k + 1, 0);
                //System.out.println("st = " + st);
                ops.add(st + 1);
                Arrays.sort(arr, st, Math.min(st + k, n));
                j += k - 1;
            }
        }
        //System.out.println(ops);
        return ops;
    }

    ArrayList<Integer> algo7(int[] inp) {
        int[] arr = new int[inp.length];
        System.arraycopy(inp, 0, arr, 0, inp.length);
        int n = inp.length;

        ArrayList<Integer> ops = new ArrayList<Integer>();
        int[] sorted = new int[n];
        System.arraycopy(inp, 0, sorted, 0, n);
        Arrays.sort(sorted);

        Random rand = new Random();

        for (int i = 0; i < n; i++) {
            if (sorted[i] == arr[i]) continue;
            int j;
            for (j = n - 1; j > i; j--) {
                if (arr[j] == sorted[i]) break;
            }
            if (rand.nextInt() % 4 > 0 || j - i < inp.length / 5) {
                ArrayList<Integer> improve = getBest(Arrays.copyOfRange(arr, i, Math.min(j + 1, arr.length)), false);
                for (int ele : improve) ops.add(ele + i);
                Arrays.sort(arr, i, Math.min(j + 1, arr.length));
            } else {
                j -= ((j - i) % (k - 1) == 0 ? k - 1 : (j - i) % (k - 1));
                //System.out.println(j + " " + i);
                while (j >= i) {
                    ops.add(j + 1);
                    Arrays.sort(arr, j, Math.min(j + k, n));
                    j -= k - 1;
                }
            }
        }
        //System.out.println(ops);
        return ops;
    }

    ArrayList<Integer> algo8(int[] inp) {
        int[] arr = new int[inp.length];
        System.arraycopy(inp, 0, arr, 0, inp.length);
        int n = inp.length;

        ArrayList<Integer> ops = new ArrayList<Integer>();
        int[] sorted = new int[n];
        System.arraycopy(inp, 0, sorted, 0, n);
        Arrays.sort(sorted);

        Random random = new Random();

        for (int i = 0; i < n; i++) {
            if (sorted[i] == arr[i]) continue;
            int j;
            for (j = n - 1; j > i; j--) {
                if (arr[j] == sorted[i]) break;
            }
            if (random.nextInt() % 3 > 0 || (j - i >= 10 && j - i <= inp.length / 5)) {
                ArrayList<Integer> improve = getBest(Arrays.copyOfRange(arr, i, Math.min(j + 1, arr.length)), false);
                for (int ele : improve) ops.add(ele + i);
                Arrays.sort(arr, i, Math.min(j + 1, arr.length));
            } else {
                j -= ((j - i) % (k - 1) == 0 ? k - 1 : (j - i) % (k - 1));
                //System.out.println(j + " " + i);
                while (j >= i) {
                    ops.add(j + 1);
                    Arrays.sort(arr, j, Math.min(j + k, n));
                    j -= k - 1;
                }
            }
        }
        //System.out.println(ops);
        return ops;
    }

    ArrayList<Integer> algo9(int[] inp) {
        int[] arr = new int[inp.length];
        System.arraycopy(inp, 0, arr, 0, inp.length);
        int n = inp.length;

        ArrayList<Integer> ops = new ArrayList<Integer>();
        int[] sorted = new int[n];
        System.arraycopy(inp, 0, sorted, 0, n);
        Arrays.sort(sorted);

        Random rand = new Random();

        for (int i = 0; i < n; i++) {
            if (sorted[i] == arr[i]) continue;
            int j;
            for (j = n - 1; j > i; j--) {
                if (arr[j] == sorted[i]) break;
            }
            if (rand.nextInt() % 3 == 0 || j - i <= inp.length / 7) {
                ArrayList<Integer> improve = getBest(Arrays.copyOfRange(arr, i, Math.min(j + 1, arr.length)), false);
                for (int ele : improve) ops.add(ele + i);
                Arrays.sort(arr, i, Math.min(j + 1, arr.length));
            } else {
                j -= ((j - i) % (k - 1) == 0 ? k - 1 : (j - i) % (k - 1));
                //System.out.println(j + " " + i);
                while (j >= i) {
                    ops.add(j + 1);
                    Arrays.sort(arr, j, Math.min(j + k, n));
                    j -= k - 1;
                }
            }
        }
        //System.out.println(ops);
        return ops;
    }

    ArrayList<Integer> algo10(int[] inp) {
        int[] arr = new int[inp.length];
        System.arraycopy(inp, 0, arr, 0, inp.length);
        int n = inp.length;

        ArrayList<Integer> ops = new ArrayList<Integer>();
        int[] sorted = new int[n];
        System.arraycopy(inp, 0, sorted, 0, n);
        Arrays.sort(sorted);

        Random random = new Random();

        for (int i = n - 1; i >= 0; i--) {
            if (sorted[i] == arr[i]) continue;
            int j;
            for (j = i - 1; j >= 0; j--) {
                if (arr[j] == sorted[i]) break;
            }
            if (random.nextInt() % 3 > 0 || i - j <= inp.length / 5) {
                ArrayList<Integer> improve = getBest(Arrays.copyOfRange(arr, j, Math.min(i + 1, arr.length)), false);
                for (int ele : improve) ops.add(ele + j);
                Arrays.sort(arr, j, Math.min(i + 1, arr.length));
            } else {
                j += ((i - j) % (k - 1) == 0 ? k - 1 : (i - j) % (k - 1));
                //System.out.println("j = " + j);
                while (j <= i) {
                    int st = Math.max(j - k + 1, 0);
                    //System.out.println("st = " + st);
                    ops.add(st + 1);
                    Arrays.sort(arr, st, Math.min(st + k, n));
                    j += k - 1;
                }
            }
        }
        //System.out.println(ops);
        return ops;
    }

    ArrayList<Integer> algo11(int[] inp) {
        int[] arr = new int[inp.length];
        System.arraycopy(inp, 0, arr, 0, inp.length);
        int n = inp.length;

        ArrayList<Integer> ops = new ArrayList<Integer>();
        int[] sorted = new int[n];
        System.arraycopy(inp, 0, sorted, 0, n);
        Arrays.sort(sorted);

        Random random = new Random();

        for (int i = n - 1; i >= 0; i--) {
            if (sorted[i] == arr[i]) continue;
            int j;
            for (j = i - 1; j >= 0; j--) {
                if (arr[j] == sorted[i]) break;
            }

            ArrayList<Integer> improve = getBest(Arrays.copyOfRange(arr, j, Math.min(i + 1, arr.length)), false);
            for (int ele : improve) ops.add(ele + j);
            Arrays.sort(arr, j, Math.min(i + 1, arr.length));

        }
        //System.out.println(ops);
        return ops;
    }
}