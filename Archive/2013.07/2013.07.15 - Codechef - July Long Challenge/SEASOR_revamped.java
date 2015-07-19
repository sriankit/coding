package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class SEASOR_revamped {
    int k, arr[];

    boolean isSorted(int from, int to) {
        for (int i = from + 1; i < to; i++) {
            if (arr[i] < arr[i - 1]) return false;
        }
        return true;
    }

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

    ArrayList<Integer> sort(int from, int to) {
        int n = to - from;
        ArrayList<Integer> ans = new ArrayList<Integer>();
        if (n <= Math.max(arr.length / 6, 3) * k) {
            int end = Math.min(from + k, arr.length);
            if (!isSorted(from, to)) {
                ArrayList<Integer> add = getBest(Arrays.copyOfRange(arr, from, to), true);
                Arrays.sort(arr, from, end);
                for (int ele : add) ans.add(from + ele);
            }
            return ans;
        }
        int q = (to - from) / k;
        if (q < 2) q = 2;
        int mid = from + (q / 2) * k;
        ans.addAll(sort(from, mid));
        ans.addAll(sort(mid, to));
        ArrayList<Integer> add = algo6(Arrays.copyOfRange(arr, from, to));
        for (int ele : add) ans.add(ele + from);
        Arrays.sort(arr, from, to);
        //System.out.println(Arrays.toString(arr));
        return ans;
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        testNumber = in.readInt();
        while (testNumber-- > 0) {
            int n = in.readInt();
            k = in.readInt();
            arr = IOUtils.readIntArray(in, n);
            ArrayList<Integer> comp = getBest(arr, true);
            ArrayList<Integer> ans = sort(0, arr.length);
            if (comp.size() < ans.size()) ans = comp;
            out.printLine(ans.size());
            for (int ele : ans) out.print(ele + " ");
            out.printLine("");
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


