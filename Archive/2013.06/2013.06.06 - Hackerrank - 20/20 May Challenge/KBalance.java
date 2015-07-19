package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

public class KBalance {
    static final int MOD = 1000000007;
    final CntSum RED = new CntSum(0, 0);
    int k;
    int truk;
    long pow10[];
    int[] arr;
    CntSum preDP[][][][];
    long inv2 = 500000004;
    private CntSum[][][] dp;

    public void solve(int testNumber, InReader in, OutputWriter out) {
        long l = in.readLong();
        long r = in.readLong();
        truk = in.readInt();

        pow10 = new long[19];
        pow10[0] = 1;
        for (int i = 1; i < 19; i++) {
            pow10[i] = (pow10[i - 1] * 10);
        }

        long ansr = compute(r);
        long ansl = compute(l - 1);
        ////System.out.println(ansl);
        ////System.out.println(ansr);
        /*long spcl = 0;
        //System.out.println("**********Printing Corrections");
        for (int i = 1; i < 19; i++) {
            long cor = correction(i, truk);
            if(i > 6 && i < 18) spcl = (spcl + cor) % MOD;
            //System.out.printf("%d %d\n", i, cor);
        }
        //System.out.println(spcl);
        //System.out.println("***********END");*/
        out.printLine((ansr - ansl + MOD) % MOD);
    }

    long compute(long num) {
        if (num <= 0) return 0;
        long res = num;
        arr = new int[19];
        int i = 0;
        int sumk = 0;
        while (res > 0) {
            arr[i++] = (int) (res % 10);
            res /= 10;
        }
        int first = i - 1;
        k = Math.min(first + 1 - truk, truk);
        long ans = 0;
        long num2 = num;
        dp = new CntSum[19][90][2];
        preDP = new CntSum[19][19][90][2];

        if (k > 0) {

            for (int sum = 1; sum <= k * 9; sum++) {
                CntSum p = find(-1, first, sum, true);
                CntSum suf = findSum(k - 1, sum, false);
                ans = (ans + (p.sum * suf.cnt) % MOD + (suf.sum * p.cnt) % MOD) % MOD;
            }

            ////System.out.println(ans);
            for (int cnt = 0; cnt < k; cnt++) {
                sumk += arr[first - cnt];
                num2 -= pow10[cnt] * (arr[cnt]);
            }
            ////System.out.println(num2);
            CntSum suf = findSum(k - 1, sumk, true);
            long cntpre = (suf.cnt > 0) ? suf.cnt : 0;
            if (suf.sum > 0) ans = (ans + suf.sum) % MOD;
            suf = findSum(k - 1, sumk, false);
            if (suf.sum > 0) ans = ((ans - suf.sum) % MOD) % MOD;
            num2 %= MOD;
            ans = (ans + (num2 * (cntpre - suf.cnt)) % MOD) % MOD;
            while (ans < 0) ans += MOD;
        }
        num2 = pow10[truk] - 1;
        ////System.out.println(num2);
        ans = (ans + sumTon(Math.min(num2, num))) % MOD;


        //trolled moment that this much is not worth of AC
        //correction phase
        for (int j = 2 * k - 1; j > truk; j--) {
            long corj = correction(j, truk);
            //System.out.printf("confirm for %d %d\n",j,corj);
            ans = (ans + corj) % MOD;
        }

        return ans;
    }

    long correction(int len, int compk) {
        k = Math.min(len - compk, compk);
        arr = new int[19];
        long res = pow10[len] - 1;
        int i = 0;
        while (res > 0) {
            arr[i++] = (int) (res % 10);
            res /= 10;
        }
        int first = i - 1;
        dp = new CntSum[19][90][2];
        preDP = new CntSum[19][19][90][2];
        long ans = 0;

        for (int sum = 1; sum <= k * 9; sum++) {
            CntSum p = find(first, first, sum, false);
            CntSum suf = findSum(k - 1, sum, false);
            ans = (ans + (p.sum * suf.cnt) % MOD + (suf.sum * p.cnt) % MOD) % MOD;
        }
        return ans;
    }

    long sumTon(long n) {
        n %= MOD;
        long num = (n * (n + 1)) % MOD;
        return (num * inv2) % MOD;
    }

    CntSum find(int start, int digs, int sum, boolean pre) {
        int lim = 9;
        int pr = (pre) ? 1 : 0;
        if (pre) lim = Math.min(arr[digs], lim);
        if (sum < 0) return RED;
        long retSum = 0;
        long retCnt = 0;
        //check end of prefix
        if (digs == k - 1) return new CntSum(1, 0);
        //establish 1st non-zero digit
        if (start == -1) {
            if (digs > Math.max(2 * k - 1, truk)) {
                CntSum node = find(-1, digs - 1, sum, false);
                retCnt = (retCnt + node.cnt) % MOD;
                retSum = (retSum + node.sum) % MOD;
            }
            start = digs;
        }
        if (preDP[start][digs][sum][pr] != null) return preDP[start][digs][sum][pr];
        //first k digits end
        if (digs == start - k + 1) {
            if (sum <= lim) {
                CntSum node = find(start, digs - 1, 0, pre && sum == arr[digs]);
                return preDP[start][digs][sum][pr] = new CntSum((retCnt + node.cnt) % MOD, (retSum + (sum * ((pow10[digs] % MOD) * node.cnt) % MOD) % MOD + node.sum) % MOD);
                //return preDP[start][digs][sum][pr] = new CntSum((retCnt + node.cnt) % MOD, (retSum + ((node.sum > 0)?(sum * (pow10[digs] % MOD) * node.cnt) % MOD + node.sum:0)) % MOD);
            } else return new CntSum(retCnt, retSum);
        }
        boolean hole = false;
        if (start - digs >= k) hole = true;

        for (int i = (start == digs) ? 1 : 0; i <= lim; i++) {
            CntSum node;
            if (!hole) node = find(start, digs - 1, sum - i, pre && i == arr[digs]);
            else node = find(start, digs - 1, 0, pre && i == arr[digs]);


            retSum = (retSum + (i * ((pow10[digs] % MOD) * node.cnt) % MOD) % MOD + node.sum) % MOD;
            retCnt = (retCnt + node.cnt) % MOD;

        }
        return preDP[start][digs][sum][pr] = new CntSum(retCnt, retSum);
    }

    CntSum findSum(int digs, int sum, boolean pre) {
        int lim = 9;
        int pr = pre ? 1 : 0;
        if (sum < 0) return new CntSum(0, 0);
        if (dp[digs][sum][pr] != null) return dp[digs][sum][pr];
        if (pre) lim = Math.min(lim, arr[digs]);
        if (digs == 0) {
            //last digit
            if (sum <= lim) return new CntSum(1, sum);
            else return RED;
        }
        long retSum = 0;
        long retCnt = 0;
        for (int i = 0; i <= lim; i++) {
            CntSum node = findSum(digs - 1, sum - i, pre && i == arr[digs]);
            long s = node.sum;
            retSum = (retSum + (i * ((pow10[digs] % MOD) * node.cnt) % MOD) % MOD + node.sum) % MOD;
            retCnt = (retCnt + node.cnt) % MOD;

        }
        return dp[digs][sum][pr] = new CntSum(retCnt, retSum);
    }
}

class CntSum {
    long sum;
    long cnt;

    CntSum(long cnt, long sum) {
        this.cnt = cnt;
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "[" + cnt + " , " + sum + "]";
    }

    public boolean equals(CntSum obj) {
        return obj.cnt == cnt && obj.sum == sum;
    }
}
