iupackage Tasks;

        import javaUtils.IOUtils;
        import javaUtils.InReader;
        import javaUtils.IntegerUtils;
        import javaUtils.OutputWriter;

        import java.util.ArrayList;
        import java.util.HashSet;

public class UnfriendlyNumbers {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        long friend = in.readLong();

        long[] unfriendly = IOUtils.readLongArray(in, n);
        ArrayList<Long> facs = new ArrayList<Long>();
        long sqrt = (long) Math.sqrt(friend);
        for (long i = 1; i < sqrt; i++) {
            if (friend % i == 0) {
                facs.add(i);
                facs.add(friend / i);
            }
        }
        if (friend % sqrt == 0) facs.add(sqrt);

        int MOD1 = 1000005;
        int MOD2 = 10000006;
        HashSet<Integer> save[] = new HashSet[1000006];
        long max = 0;
        ArrayList<Long> gcds = new ArrayList<Long>();
        for (int i = 0; i < n; i++) {
            long g = IntegerUtils.gcd(unfriendly[i], friend);
            int res1 = (int) (g % MOD1);
            int res2 = (int) (g % MOD2);
            if (save[res1] == null) {
                save[res1] = new HashSet<Integer>();
                save[res1].add(res2);
                gcds.add(g);
            } else if (!(save[res1].contains(res2))) {
                save[res1].add(res2);
                gcds.add(g);
            }
            max = Math.max(max, g);
        }

        System.out.println(max);
        long ans = 0;
        System.out.println(facs.size());
        System.out.println(gcds.size());
        for (long fac : facs) {
            boolean fail = false;
            for (long unfriend : gcds) {
                if (unfriend % fac == 0) {
                    fail = true;
                    break;
                }
            }
            if (!fail) ans++;
        }
        out.printLine(ans);
    }
}
