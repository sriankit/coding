package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.*;

public class SEASOR {
    int k;
    int calls = 0;
    long TL = 599;
    long start;

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
            if (rand.nextInt() % 4 > 0 && j - i < inp.length / 5) {
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
            if (random.nextInt() % 4 > 0 || i - j <= inp.length / 5) {
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

    ArrayList<Integer> algo12(int[] inp) {
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
            for (j = 0; j < i; j++) {
                if (arr[j] == sorted[i]) break;
            }
            try {
                int end = i + (random.nextInt(k)) + 1;
                //System.out.println(i + " " + j +" " + n);
                ArrayList<Integer> improve = getBest(Arrays.copyOfRange(arr, j, Math.min(end, arr.length)), false);
                for (int ele : improve) ops.add(ele + j);
                Arrays.sort(arr, j, Math.min(end, arr.length));
            } catch (Exception e) {
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

    boolean check(int[] ar, ArrayList<Integer> inst) {
        int[] arr = new int[ar.length];
        System.arraycopy(ar, 0, arr, 0, ar.length);
        for (int ins : inst) {
            Arrays.sort(arr, ins, Math.min(ins + k, arr.length));
        }
        boolean ok = true;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                ok = false;
                break;
            }
        }
        return ok;
    }

    ArrayList<Integer> bruteSortCaller(int[] inp) {
        int[] b = new int[inp.length];
        Arrays.sort(b);
        int end = inp.length - 1;
        int st = 0;
        while (b[st] == inp[st]) st++;
        while (b[end] == inp[end]) end--;
        if (end < st) return new ArrayList<Integer>(0);
        else return bruteSort(Arrays.copyOfRange(inp, st, end + 1));
    }

    ArrayList<Integer> bruteSort(int[] inp) {
        --calls;
        boolean ok = true;
        for (int i = 1; i < inp.length; i++) {
            if (inp[i] < inp[i - 1]) {
                ok = false;
                break;
            }
        }
        if (ok) return new ArrayList<Integer>(0);
        Queue<ArrayList<Integer>> list = new LinkedList<ArrayList<Integer>>();
        for (int i = 0; i < inp.length; i++) {
            ArrayList<Integer> add = new ArrayList<Integer>();
            add.add(i);
            list.add(add);
        }
        while (!list.isEmpty()) {
            ArrayList<Integer> test = list.poll();
            boolean result = check(inp, test);
            if (result) {
                for (int i = 0; i < test.size(); i++) {
                    test.set(i, test.get(i) + 1);
                }
                return test;
            } else {
                for (int i = 0; i < inp.length; i++) {
                    ArrayList<Integer> anon = new ArrayList<Integer>(test);
                    anon.add(i);
                    list.offer(anon);
                }
            }
        }
        return null;
    }



    /*ArrayList<Integer> bruteSort(int[] inp) {
        boolean ok = true;
        for (int i = 1; i < inp.length; i++) {
            if(inp[i] < inp[i - 1]) {
                ok = false;
                break;
            }
        }
        if(ok) return new ArrayList<Integer>(0);
        for (int i = 0; i < inp.length; i++) {
            Stack<Integer> stack = go(inp, i, 0);
            if(stack != null) {
                ArrayList<Integer> ans = new ArrayList<Integer>();
                while(!stack.empty()) ans.add(stack.pop());
                return ans;
            }
        }
        return null;
    }

    Stack<Integer> go(int[] ar, int st, int done) {
        if(done == 6) return null;
        int[] b = new int[ar.length];
        System.arraycopy(ar, 0, b, 0 ,ar.length);
        Arrays.sort(b, st, Math.min(st + k, ar.length));
        boolean ok = true;
        for (int i = 1; i < ar.length; i++) {
            if(b[i] < b[i - 1]) {
                ok = false;
                break;
            }
        }
        if(ok) {
            Stack<Integer> ret = new Stack<Integer>();
            ret.push(st);
            return ret;
        } else {
            for (int i = 0; i < b.length; i++) {
                Stack<Integer> ret = go(b, i, done + 1);
                if(ret != null) {
                    ret.push(st);
                    return ret;
                }
            }
            return null;
        }
    } */

    public void solve(int testNumber, InReader in, OutputWriter out) {
        //System.out.println("done " + testNumber );
        start = System.currentTimeMillis();
        int n;
        n = 1000;
        k = 2;
        //int[] arr = IOUtils.readIntArray(in, n);
        int[] arr = new int[1000];
        for (int i = 0; i < 1000; i++) {
            arr[i] = 1000 - i;
        }
        ArrayList<Integer> ans = getBest(arr, true);
        out.printLine(ans.size());
        for (int ele : ans) out.print(ele + " ");
        out.printLine("");
    }

    ArrayList<Integer> getBest(int[] arr, boolean svn) {
        /*if (arr.length < 7) {
            ArrayList<Integer> ans = bruteSortCaller(arr);
            if (ans != null) {
                return ans;
            }
        } */
        ArrayList<ArrayList<Integer>> answers = new ArrayList<ArrayList<Integer>>();
        answers.add(algo1(arr));
        answers.add(algo2(arr));
        answers.add(algo3(arr));
        answers.add(algo4(arr));
        answers.add(algo5(arr));
        answers.add(algo6(arr));
        if (svn) {
            answers.add(algo7(arr));
            answers.add(algo8(arr));
            answers.add(algo9(arr));
            answers.add(algo10(arr));
            //answers.add(algo11(arr));
            //answers.add(algo12(arr));
        }
        Collections.sort(answers, new Comparator<ArrayList<Integer>>() {
            @Override
            public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
                return o1.size() - o2.size();
            }
        });
        ArrayList<Integer> best = answers.get(0);


        int[] ar2 = arr.clone();
        ArrayList<Integer> ops = new ArrayList<Integer>();
        boolean ok = true;
        int n = arr.length;
        for (int i = n - 2; i >= 0; --i) {
            if (arr[i] > arr[i + 1]) {
                int st = Math.max(i + 1 - k + 1, 0);
                ops.add(st + 1);
                Arrays.sort(arr, st, Math.min(st + k, n));
                ok = false;
            }
        }
        if (true) return best;
        answers.clear();
        //answers.add(algo1(ar2));
        answers.add(algo2(arr));
        //answers.add(algo3(ar2));
        answers.add(algo4(arr));
        answers.add(algo5(arr));
        answers.add(algo6(arr));
        //if(TL - System.currentTimeMillis() < 10) return best;
        if (svn) {
            answers.add(algo7(arr));
            answers.add(algo8(arr));
            answers.add(algo9(arr));
            answers.add(algo10(arr));
            answers.add(algo11(arr));
            //answers.add(algo12(ar2));
        }
        Collections.sort(answers, new Comparator<ArrayList<Integer>>() {
            @Override
            public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
                return o1.size() - o2.size();
            }
        });
        ops.addAll(answers.get(0));
        if (!check(arr, ops)) return best;
        //System.out.println("ops = " + ops);
        //System.out.println("best = " + best);

        if (ops.size() < best.size()) return ops;
        else return best;
    }
}
